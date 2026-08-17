package com.gujeuk.monitorbot.probe;

import com.gujeuk.monitorbot.config.MonitorProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Duration;

/**
 * 공인 도메인으로 실제 호출해 외부 접근성을 확인한다.
 * <p>
 * Prometheus 스크레이프마다 호출되므로(기본 15초) 결과를 캐시하지 않는다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EndpointProbe {

    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    private final MonitorProperties properties;
    private final HttpProbe httpProbe;

    /** 외부 도메인 → Caddy → 앱 경로가 살아있는지. DNS·TLS·프록시 문제까지 포함해 잡힌다. */
    public HttpProbe.Result publicHealth() {
        return call(properties.probe().publicHealthUrl());
    }

    /** 헬스체크만으로는 라우팅·시큐리티 설정이 깨진 걸 못 잡아서 실제 조회 API도 호출한다. */
    public HttpProbe.Result publicApi() {
        return call(properties.probe().publicApiUrl());
    }

    private HttpProbe.Result call(String url) {
        if (!StringUtils.hasText(url)) {
            return null;
        }
        HttpProbe.Result result = httpProbe.get(url, TIMEOUT);
        if (!result.isSuccess()) {
            log.warn("외부 점검 실패: {} - {}", url, result.reached() ? "HTTP " + result.statusCode() : result.error());
        }
        return result;
    }
}
