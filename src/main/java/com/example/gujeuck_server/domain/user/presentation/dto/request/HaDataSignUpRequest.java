package com.example.gujeuck_server.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.domain.user.domain.enums.Gender;

import java.time.LocalDateTime;

public record HaDataSignUpRequest(
        String clientRecordId,

        @NotBlank(message = "이름을 입력해주세요.")
        @Size(max = 30, message = "이름은 30자 이하로 입력해주세요.")
        String name,

        @NotBlank(message = "전화번호를 입력해주세요.")
        @Size(max = 20, message = "전화번호는 20자 이하로 입력해주세요.")
        String phone,
        Gender gender,
        String birthYMD,
        Age age,
        @Size(max = 30, message = "거주지는 30자 이하로 입력해주세요.")
        String residence,
        boolean privacyAgreed,
        int maleCount,
        int femaleCount,
        @Size(max = 30, message = "방문목적은 30자 이하로 입력해주세요.")
        String purpose,
        LocalDateTime visitTime
) {
}
