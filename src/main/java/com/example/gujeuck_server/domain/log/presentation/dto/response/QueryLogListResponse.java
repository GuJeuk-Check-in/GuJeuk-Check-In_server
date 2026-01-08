package com.example.gujeuck_server.domain.log.presentation.dto.response;

import com.example.gujeuck_server.domain.log.domain.Log;
import lombok.Builder;

@Builder
public record QueryLogListResponse(

        Long id,
        String name,
        int maleCount,
        int femaleCount,
        String visitDate
) {

    public static QueryLogListResponse from(Log log) {
        return QueryLogListResponse.builder()
                .id(log.getId())
                .name(log.getName())
                .maleCount(log.getMaleCount())
                .femaleCount(log.getFemaleCount())
                .visitDate(log.getVisitDate())
                .build();
    }
}
