package com.example.gujeuck_server.domain.organ.presentation.dto.response;

import com.example.gujeuck_server.domain.log.domain.VisitStatisticsCount;

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
        return new VisitStatisticsResponse(
                year,
                month,
                VisitPeriodStatisticsResponse.from(cumulative),
                VisitPeriodStatisticsResponse.from(monthly)
        );
    }
}
