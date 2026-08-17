package com.gujeuk.monitorbot.probe;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLHandshakeException;

/**
 * HTTP 점검용 공통 호출기. 실패 원인을 사람이 읽을 수 있는 문구로 바꿔준다.
 */
@Component
public class HttpProbe {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    public Result get(String url, Duration timeout) {
        long startedAt = System.nanoTime();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(timeout)
                    .GET()
                    .build();
            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            return new Result(true, response.statusCode(), elapsedMs(startedAt), null);
        } catch (Exception e) {
            return new Result(false, -1, elapsedMs(startedAt), describe(e));
        }
    }

    private long elapsedMs(long startedAt) {
        return TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startedAt);
    }

    /**
     * 예외 타입별로 무엇이 끊겼는지 구분해 준다. 네트워크·DNS·인증서 문제를 알림만 보고 구분하기 위함이다.
     */
    private String describe(Exception e) {
        Throwable cause = rootCause(e);
        if (cause instanceof UnknownHostException) {
            return "DNS 조회 실패 (도메인을 찾을 수 없음)";
        }
        if (cause instanceof SSLHandshakeException) {
            return "TLS 핸드셰이크 실패 (인증서 문제 가능)";
        }
        if (cause instanceof HttpTimeoutException) {
            return "응답 시간 초과";
        }
        if (cause instanceof ConnectException) {
            return "연결 거부 (서비스가 떠 있지 않거나 포트가 막힘)";
        }
        if (cause instanceof IOException) {
            return "네트워크 오류: " + cause.getClass().getSimpleName();
        }
        return cause.getClass().getSimpleName() + (cause.getMessage() == null ? "" : ": " + cause.getMessage());
    }

    private Throwable rootCause(Throwable t) {
        Throwable current = t;
        while (current.getCause() != null && current.getCause() != current) {
            current = current.getCause();
        }
        return current;
    }

    public record Result(boolean reached, int statusCode, long latencyMs, String error) {

        public boolean isSuccess() {
            return reached && statusCode >= 200 && statusCode < 300;
        }
    }
}
