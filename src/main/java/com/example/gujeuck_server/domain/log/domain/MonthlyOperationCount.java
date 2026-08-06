package com.example.gujeuck_server.domain.log.domain;

public record MonthlyOperationCount(
        long operatingDays,
        long totalVisitors
) {
}
