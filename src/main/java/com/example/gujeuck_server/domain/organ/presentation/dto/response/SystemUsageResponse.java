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
    public static SystemUsageResponse of(List<SystemUsageOneResponse> systemUsageOneResponses) {
        return SystemUsageResponse.builder()
            .total(systemUsageOneResponses.get(0))
            .january(systemUsageOneResponses.get(1))
            .february(systemUsageOneResponses.get(2))
            .march(systemUsageOneResponses.get(3))
            .april(systemUsageOneResponses.get(4))
            .may(systemUsageOneResponses.get(5))
            .june(systemUsageOneResponses.get(6))
            .july(systemUsageOneResponses.get(7))
            .august(systemUsageOneResponses.get(8))
            .september(systemUsageOneResponses.get(9))
            .october(systemUsageOneResponses.get(10))
            .december(systemUsageOneResponses.get(11))
            .build();
    }
}
