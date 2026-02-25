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
    public QueryLogDetailResponse(Log log) {
        this(
                log.getId(),
                log.getName(),
                log.getAge(),
                log.getPhone(),
                log.getMaleCount(),
                log.getFemaleCount(),
                log.getPurpose(),
                log.getVisitDate(),
                log.getVisitTime(),
                log.isPrivacyAgreed()
        );
    }
}