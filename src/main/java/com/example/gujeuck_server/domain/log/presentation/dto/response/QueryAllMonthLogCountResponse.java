package com.example.gujeuck_server.domain.log.presentation.dto.response;

import java.util.List;

public record QueryAllMonthLogCountResponse(
    List<MonthlyLogCountResponse> months
) {
    public static QueryAllMonthLogCountResponse of(List<MonthlyLogCountResponse> months) {
        return new QueryAllMonthLogCountResponse(months);
    }
}
