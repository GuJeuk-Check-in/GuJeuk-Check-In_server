package com.example.gujeuck_server.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserCheckInRequest (
    @NotNull(message = "userId를 입력해주세요.")
    Long userId,

    @NotNull(message = "남자 동행인 수는 null일 수 없습니다.")
    @PositiveOrZero(message = "남자 동행인 수는 0 이상이어야 합니다.")
    Integer maleCount,

    @NotNull(message = "여자 동행인 수는 null일 수 없습니다.")
    @PositiveOrZero(message = "여자 동행인 수는 0 이상이어야 합니다.")
    Integer femaleCount,

    @NotBlank(message = "방문목적을 입력해주세요.")
    String purpose,

    @NotNull(message = "방문시각을 비워둘 수 없습니다.")
    LocalDateTime visitTime,

    // HA 경로에서만 전달됨. 일반 check-in 요청은 null.
    String clientRecordId
) {
    public static UserCheckInRequest create(Long userId, Integer maleCount, Integer femaleCount, String purpose, LocalDateTime visitTime, String clientRecordId) {
        return UserCheckInRequest.builder()
            .userId(userId)
            .maleCount(maleCount)
            .femaleCount(femaleCount)
            .purpose(purpose)
            .visitTime(visitTime)
            .clientRecordId(clientRecordId)
            .build();
    }
}
