package com.gujeuk.monitorbot.health;

public record AppHealthStatus(
        String targetName,
        boolean up
) {
}
