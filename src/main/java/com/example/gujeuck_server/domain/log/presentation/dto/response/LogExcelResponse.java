package com.example.gujeuck_server.domain.log.presentation.dto.response;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import lombok.Builder;

@Builder
public record LogExcelResponse(

        String visitDate,
        String visitTime,
        String name,
        Age age,
        int maleCount,
        int femaleCount,
        String phone,
        String purpose,
        boolean privacyAgreed
) {
    // Log 엔티티 -> LogResponse 로 바꿔주는 정적 메서드
    public static LogExcelResponse from(Log log) {
        return LogExcelResponse.builder()
                .name(log.getName())
                .phone(log.getPhone())
                .maleCount(log.getMaleCount())
                .femaleCount(log.getFemaleCount())
                .visitDate(log.getVisitDate())
                .visitTime(log.getVisitTime())
                .purpose(log.getPurpose())
                .age(log.getAge())
                .privacyAgreed(log.isPrivacyAgreed())
                .build();
    }
}
