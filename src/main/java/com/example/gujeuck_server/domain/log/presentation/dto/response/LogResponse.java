package com.example.gujeuck_server.domain.log.presentation.dto.response;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogResponse {

    private String name;

    private String phone;

    private int maleCount;

    private int femaleCount;

    private String visitDate;

    private String visitTime;

    private String purpose;

    private Age age;

    private String residence;

    private boolean privacyAgreed;

    // Log 엔티티 -> LogResponse 로 바꿔주는 정적 메서드
    public static LogResponse from(Log log) {
        return LogResponse.builder()
                .name(log.getName())
                .phone(log.getPhone())
                .maleCount(log.getMaleCount())
                .femaleCount(log.getFemaleCount())
                .visitDate(log.getVisitDate())
                .visitTime(log.getVisitTime())
                .purpose(log.getPurpose())
                .age(log.getAge())
                .residence(log.getResidence())
                .privacyAgreed(log.isPrivacyAgreed())
                .build();
    }
}
