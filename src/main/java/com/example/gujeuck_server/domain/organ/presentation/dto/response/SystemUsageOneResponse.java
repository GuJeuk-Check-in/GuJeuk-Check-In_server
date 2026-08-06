package com.example.gujeuck_server.domain.organ.presentation.dto.response;

import lombok.Builder;

@Builder
public record SystemUsageOneResponse(
    int opDate,
    int avgRate
) {
    public static SystemUsageOneResponse of(int opDate, int avgRate) {
        return SystemUsageOneResponse.builder()
            .opDate(opDate)
            .avgRate(avgRate)
            .build();
    }
}
