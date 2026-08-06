package com.example.gujeuck_server.domain.organ.presentation.dto.response;

import lombok.Builder;

@Builder
public record SystemUsageOneResponse(
    Integer opDate,
    Double avgRate
) {
    public static SystemUsageOneResponse of(Integer opDate, Double avgRate) {
        return SystemUsageOneResponse.builder()
            .opDate(opDate)
            .avgRate(avgRate)
            .build();
    }

    public static SystemUsageOneResponse empty() {
        return SystemUsageOneResponse.of(null, null);
    }
}
