package com.example.gujeuck_server.domain.log.dto.response;

import com.example.gujeuck_server.domain.user.entity.enums.Age;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

    public LogResponse(
            String visitDate,
            String visitTime,
            String name,
            Age age,
            Integer maleCount,
            Integer femaleCount,
            String phone,
            String purpose,
            String residence,
            Boolean privacyAgreed
    ) {
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.name = name;
        this.age = age;
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
        this.phone = phone;
        this.purpose = purpose;
        this.residence = residence;
        this.privacyAgreed = privacyAgreed;
    }
}
