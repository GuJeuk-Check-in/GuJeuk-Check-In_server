package com.gujeuk.monitorbot.docker;

public record ContainerStatus(
        String containerName,
        ContainerState state,
        HealthState health
) {
}
