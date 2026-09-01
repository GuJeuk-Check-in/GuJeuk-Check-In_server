package com.example.gujeuck_server.domain.log.presentation.dto.response;

public record MonthlyOperationCount(
        long operatingDays,
        long totalVisitors
) {
}
