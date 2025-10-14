package com.example.gujeuck_server.domain.log.dto.response;

import com.example.gujeuck_server.domain.user.entity.enums.Age;
import lombok.Getter;

@Getter
public class LogResponse {
    private String name;

    private String phone;

    private int maleCount;

    private int femaleCount;

    private String visitDate;

    private String purpose;

    private Age age;

    private boolean privacyAgreed;

}
