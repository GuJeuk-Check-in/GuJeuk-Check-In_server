package com.example.gujeuck_server.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.domain.user.domain.enums.Gender;

import java.time.LocalDateTime;

public record HaDataSignUpRequest(
        String clientRecordId,

        @NotBlank(message = "이름을 입력해주세요.")
        String name,

        @NotBlank(message = "전화번호를 입력해주세요.")
        String phone,
        Gender gender,
        String birthYMD,
        Age age,
        String residence,
        boolean privacyAgreed,
        int maleCount,
        int femaleCount,
        String purpose,
        LocalDateTime visitTime
) {
}
