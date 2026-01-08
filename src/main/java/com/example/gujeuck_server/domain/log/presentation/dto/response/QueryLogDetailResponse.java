package com.example.gujeuck_server.domain.log.presentation.dto.response;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryLogDetailResponse {
    private Long id;

    private String name;

    private Age age;

    private String phone;

    private int maleCount;

    private int femaleCount;

    private String purpose;

    private String visitDate;

    private boolean privacyAgreed;


    public QueryLogDetailResponse(Log log) {
        this.id = log.getId();
        this.name = log.getName();
        this.age = log.getAge();
        this.phone = log.getPhone();
        this.maleCount = log.getMaleCount();
        this.femaleCount = log.getFemaleCount();
        this.purpose = log.getPurpose();
        this.visitDate = log.getVisitDate();
        this.privacyAgreed = log.isPrivacyAgreed();
    }
}
