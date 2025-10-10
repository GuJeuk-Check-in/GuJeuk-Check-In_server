package com.example.gujeuck_server.domain.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UseListRequest {
    private String name;
    private String age;
    private String phone;

    private int maleCount;

    private int femaleCount;

    private Long purposeId;

    private LocalDate visitDate;
}