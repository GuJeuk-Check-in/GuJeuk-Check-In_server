package com.example.gujeuck_server.domain.organ.presentation.dto.response;

import com.example.gujeuck_server.domain.log.domain.VisitStatisticsCount;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.Builder;

@Builder
public record VisitPeriodStatisticsResponse(
        long total,
        VisitGroupStatisticsResponse youth,
        VisitGroupStatisticsResponse other
) {

    public static VisitPeriodStatisticsResponse from(VisitStatisticsCount count) {
        long youthTotal = count.youthMale() + count.youthFemale();
        long otherTotal = count.otherMale() + count.otherFemale();
        long total = youthTotal + otherTotal;

        return VisitPeriodStatisticsResponse.builder()
                .total(total)
                .youth(VisitGroupStatisticsResponse.of(
                        count.youthMale(),
                        count.youthFemale(),
                        youthTotal,
                        calculateRate(youthTotal, total)
                ))
                .other(VisitGroupStatisticsResponse.of(
                        count.otherMale(),
                        count.otherFemale(),
                        otherTotal,
                        calculateRate(otherTotal, total)
                ))
                .build();
    }

    private static double calculateRate(long count, long total) {
        if (total == 0) {
            return 0.0;
        }

        return BigDecimal.valueOf(count)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(total), 1, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
