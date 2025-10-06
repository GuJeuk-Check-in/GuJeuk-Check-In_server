package com.example.gujeuck_server.domain.User.dto.request;

import com.example.gujeuck_server.domain.User.entity.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignupRequest {

    @NotBlank
    private String name;

    @NotBlank
    private Gender gender;

    @NotBlank
    private String phone;

    @NotBlank
    private String birthYMD;

    @NotBlank
    private String residence;

    @NotBlank
    private boolean privacyAgreed;
}
