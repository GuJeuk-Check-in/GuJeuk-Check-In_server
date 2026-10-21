package com.example.burserker.gujeuk.domain.organ.presentation.dto.response.organ;

public record MonthlyOperationCount(
        long operatingDays,
        long totalVisitors
) {
}
