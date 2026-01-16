package com.example.gujeuck_server.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotBlank(message = "이름은 필수 입력 항목입니다.")
        @Size(max = 30, message = "이름은 30자 이하로 입력해주세요.")
        String name,

        @NotBlank(message = "user ID는 필수 입력 항목입니다.")
        @Size(max = 30, message = "user ID는 30자 이하로 입력해주세요.")
        String userId,

        @NotBlank(message = "전화번호를 입력해주세요.")
        @Size(max = 11, message = "전화번호는 11자리 이상으로 입력할 수 없습니다.")
        String phone,

        @NotBlank
        String birthYMD,

        @NotBlank
        String residence
) {
}
