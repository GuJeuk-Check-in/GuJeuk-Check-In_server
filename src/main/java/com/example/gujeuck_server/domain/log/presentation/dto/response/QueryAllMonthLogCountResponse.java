package com.example.gujeuck_server.domain.log.presentation.dto.response;

import lombok.Builder;

@Builder
public record QueryAllMonthLogCountResponse(
    Integer january,
    Integer february,
    Integer march,
    Integer april,
    Integer may,
    Integer june,
    Integer july,
    Integer august,
    Integer september,
    Integer october,
    Integer november,
    Integer december
) {
    public static QueryAllMonthLogCountResponse of(
        Integer january,
        Integer february,
        Integer march,
        Integer april,
        Integer may,
        Integer june,
        Integer july,
        Integer august,
        Integer september,
        Integer october,
        Integer november,
        Integer december
    ) {
        return QueryAllMonthLogCountResponse.builder()
            .january(january)
            .february(february)
            .march(march)
            .april(april)
            .may(may)
            .june(june)
            .july(july)
            .august(august)
            .september(september)
            .october(october)
            .november(november)
            .december(december)
            .build();
    }
}
