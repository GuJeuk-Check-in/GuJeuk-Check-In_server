package com.example.gujeuck_server.domain.organ.presentation.dto.response.organ;

public record MonthlyOperationCount(
        long operatingDays,
        long totalVisitors
) {
}
