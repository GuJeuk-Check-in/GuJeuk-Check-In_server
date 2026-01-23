package com.example.gujeuck_server.domain.organ.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrganRequest {

    @NotBlank(message = "기관명을 입력해주세요.")
    private String organName;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "Client를 식별하지 못했습니다.")
    private String client;
}
