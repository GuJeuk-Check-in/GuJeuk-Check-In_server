package com.example.gujeuck_server.domain.log.presentation.dto.response;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import lombok.Builder;

@Builder
public record QueryLogDetailResponse(
        Long id,
        String name,
        Age age,
        String phone,
        int maleCount,
        int femaleCount,
        String purpose,
        String visitDate,
        String visitTime,
        boolean privacyAgreed
) {
    public static QueryLogDetailResponse from(Log log) {
        return QueryLogDetailResponse.builder()
            .id(log.getId())
            .name(log.getName())
            .age(log.getAge())
            .phone(log.getPhone())
            .maleCount(log.getMaleCount())
            .femaleCount(log.getFemaleCount())
            .purpose(log.getPurpose())
            .visitDate(log.getVisitDate())
            .visitTime(log.getVisitTime())
            .privacyAgreed(log.isPrivacyAgreed())
            .build();
    }
}