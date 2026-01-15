package com.example.gujeuck_server.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "userId를 입력해주세요")
    private String userId;

    @NotBlank(message = "방문목적을 입력해주세요")
    private String purpose;

    @NotNull(message = "남자 동행인 수는 null일 수 없습니다.")
    private int maleCount;

    @NotNull(message = "여자 동행인 수는 null일 수 없습니다.")
    private int femaleCount;
}
