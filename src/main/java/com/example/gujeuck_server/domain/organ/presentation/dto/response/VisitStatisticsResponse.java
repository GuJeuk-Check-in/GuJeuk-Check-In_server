package com.example.gujeuck_server.domain.organ.presentation.dto.response;

import com.example.gujeuck_server.domain.log.domain.VisitStatisticsCount;

import lombok.Builder;

@Builder
public record VisitStatisticsResponse(
        int year,
        int month,
        VisitPeriodStatisticsResponse cumulative,
        VisitPeriodStatisticsResponse monthly
) {

    public static VisitStatisticsResponse of(
            int year,
            int month,
            VisitStatisticsCount cumulative,
            VisitStatisticsCount monthly
    ) {
        return VisitStatisticsResponse.builder()
                .year(year)
                .month(month)
                .cumulative(VisitPeriodStatisticsResponse.from(cumulative))
                .monthly(VisitPeriodStatisticsResponse.from(monthly))
                .build();
    }
}
