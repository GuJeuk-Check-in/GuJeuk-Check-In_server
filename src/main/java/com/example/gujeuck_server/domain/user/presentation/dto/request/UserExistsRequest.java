package com.example.gujeuck_server.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserExistsRequest {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    @Size(max = 30, message = "이름은 30자 이하로 입력해주세요.")
    private String name;

    private String phone;
}
