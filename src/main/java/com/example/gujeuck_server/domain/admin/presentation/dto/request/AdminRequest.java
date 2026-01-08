package com.example.gujeuck_server.domain.admin.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminRequest {

    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
}
