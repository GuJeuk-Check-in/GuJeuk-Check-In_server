package com.example.gujeuck_server.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
    @PositiveOrZero(message = "남자 동행인 수는 0 이상이어야 합니다.")
    private Integer maleCount;

    @NotNull(message = "여자 동행인 수는 null일 수 없습니다.")
    @PositiveOrZero(message = "여자 동행인 수는 0 이상이어야 합니다.")
    private Integer femaleCount;
}
