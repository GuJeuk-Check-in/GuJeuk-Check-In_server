package com.example.gujeuck_server.domain.organ.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginOrganRequest {

    @NotBlank(message = "기관명을 입력해주세요.")
    private String organName;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
