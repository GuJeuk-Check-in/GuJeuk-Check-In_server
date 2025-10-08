package com.example.gujeuck_server.domain.admin.dto;

import com.example.gujeuck_server.domain.user.entity.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompanionDto {
    private String name;
    private String age;
    private Gender gender;
    private String phone;
}
