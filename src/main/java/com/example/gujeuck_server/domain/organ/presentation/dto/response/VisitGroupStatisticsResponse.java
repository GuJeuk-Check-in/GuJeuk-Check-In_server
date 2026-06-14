package com.example.gujeuck_server.domain.organ.presentation.dto.response;

public record VisitGroupStatisticsResponse(
        long male,
        long female,
        long total,
        double rate
) {
}
