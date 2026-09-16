package com.example.gujeuck_server.domain.organ.presentation.dto.response;

import lombok.Builder;

@Builder
public record VisitGroupStatisticsResponse(
        long male,
        long female,
        long total,
        double rate
) {
    public static VisitGroupStatisticsResponse of(long male, long female, long total, double rate) {
        return VisitGroupStatisticsResponse.builder()
                .male(male)
                .female(female)
                .total(total)
                .rate(rate)
                .build();
    }
}
