package com.example.gujeuck_server.domain.user.presentation.dto.request;

import com.example.gujeuck_server.domain.user.domain.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotBlank(message = "이름은 필수 입력 항목입니다.")
        @Size(max = 30, message = "이름은 30자 이하로 입력해주세요.")
        String name,

        String phone,

        @NotNull(message = "성별을 선택해주세요.")
        Gender gender,

        @NotBlank(message = "생년월일을 입력해주세요.")
        String birthYMD,

        @NotBlank(message = "거주지를 선택해주세요.")
        String residence
) {
}
