package com.example.gujeuck_server.domain.organ.presentation.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record SystemUsageResponse(
    SystemUsageOneResponse total,
    SystemUsageOneResponse january,
    SystemUsageOneResponse february,
    SystemUsageOneResponse march,
    SystemUsageOneResponse april,
    SystemUsageOneResponse may,
    SystemUsageOneResponse june,
    SystemUsageOneResponse july,
    SystemUsageOneResponse august,
    SystemUsageOneResponse september,
    SystemUsageOneResponse october,
    SystemUsageOneResponse november,
    SystemUsageOneResponse december
    ) {
    public static SystemUsageResponse of(SystemUsageOneResponse total, List<SystemUsageOneResponse> months) {
        return SystemUsageResponse.builder()
            .total(total)
            .january(months.get(0))
            .february(months.get(1))
            .march(months.get(2))
            .april(months.get(3))
            .may(months.get(4))
            .june(months.get(5))
            .july(months.get(6))
            .august(months.get(7))
            .september(months.get(8))
            .october(months.get(9))
            .november(months.get(10))
            .december(months.get(11))
            .build();
    }
}
