package com.gujeuk.monitorbot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "monitor")
public record MonitorProperties(
        Discord discord,
        List<Target> targets,
        List<String> extraContainers,
        int healthCheckTimeoutSeconds,
        Probe probe
) {
    public record Discord(
            String botToken,
            String guildId,
            String channelId,
            String webhookUrl
    ) {
    }

    public record Target(
            String name,
            String healthCheckUrl,
            String appContainer,
            String dbContainer,
            String redisContainer
    ) {
    }

    /**
     * 외부에서 실제로 접근되는지 확인할 대상.
     * <p>
     * 앱 컨테이너는 멀쩡한데 DNS·Caddy·인증서가 끊기면 사용자는 못 쓰는데 내부 헬스체크는 통과한다.
     * 그 구간을 잡기 위해 공인 도메인으로 한 번 더 호출한다.
     * 임계치 판단은 Grafana 알림 규칙이 하므로 여기서는 값만 수집한다.
     */
    public record Probe(
            String publicHealthUrl,
            String publicApiUrl
    ) {
    }
}
