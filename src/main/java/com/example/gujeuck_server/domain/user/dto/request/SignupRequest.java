package com.example.gujeuck_server.domain.user.dto.request;

import com.example.gujeuck_server.domain.user.entity.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SignupRequest {

    @NotBlank
    private String name;

    @NotNull
    private Gender gender;

    @NotBlank
    private String phone;

    @NotNull
    private int maleCount;

    @NotNull
    private int femaleCount;

    @NotBlank
    private String birthYMD;

    @NotBlank
    private String residence;

    @NotNull
    private boolean privacyAgreed;

    @NotBlank
    private String purpose;
}
