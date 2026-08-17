package com.gujeuk.monitorbot.probe;

import com.gujeuk.monitorbot.config.MonitorProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * TLS 인증서 잔여 일수를 잰다. Caddy 자동 갱신이 실패하면 어느 날 갑자기 전체가 접속 불가가 된다.
 * <p>
 * 핸드셰이크 비용이 있어 결과를 30분간 재사용한다. 인증서 만료일은 그 사이 바뀌지 않는다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TlsCertificateProbe {

    private static final int HANDSHAKE_TIMEOUT_MS = (int) Duration.ofSeconds(10).toMillis();
    private static final Duration CACHE_TTL = Duration.ofMinutes(30);
    private static final int DEFAULT_HTTPS_PORT = 443;

    /** 확인 불가 상태. Grafana 규칙에서 만료 임박(작은 양수)과 구분하기 위해 음수로 둔다. */
    private static final double UNKNOWN = -1;

    private final MonitorProperties properties;

    private volatile double cachedDaysRemaining = UNKNOWN;
    private volatile Instant cachedAt = Instant.EPOCH;

    public double daysRemaining() {
        String url = properties.probe().publicHealthUrl();
        if (!StringUtils.hasText(url) || !url.startsWith("https://")) {
            return UNKNOWN;
        }
        if (Instant.now().isBefore(cachedAt.plus(CACHE_TTL))) {
            return cachedDaysRemaining;
        }

        double measured = measure(URI.create(url));
        cachedDaysRemaining = measured;
        cachedAt = Instant.now();
        return measured;
    }

    private double measure(URI uri) {
        String host = uri.getHost();
        int port = uri.getPort() == -1 ? DEFAULT_HTTPS_PORT : uri.getPort();

        try (SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket()) {
            socket.connect(new InetSocketAddress(host, port), HANDSHAKE_TIMEOUT_MS);
            socket.setSoTimeout(HANDSHAKE_TIMEOUT_MS);
            socket.startHandshake();

            X509Certificate certificate = (X509Certificate) socket.getSession().getPeerCertificates()[0];
            return ChronoUnit.DAYS.between(Instant.now(), certificate.getNotAfter().toInstant());
        } catch (Exception e) {
            log.warn("TLS 인증서 확인 실패: {}:{}", host, port, e);
            return UNKNOWN;
        }
    }
}
