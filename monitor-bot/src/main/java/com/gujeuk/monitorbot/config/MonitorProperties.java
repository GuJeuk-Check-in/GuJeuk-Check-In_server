package com.gujeuk.monitorbot.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "monitor")
public record MonitorProperties(
        Discord discord,
        String reportCron,
        List<Target> targets,
        List<String> extraContainers,
        int healthCheckTimeoutSeconds
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
}
