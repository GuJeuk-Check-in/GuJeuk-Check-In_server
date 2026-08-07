package com.example.gujeuck_server.domain.common.presentation.funnel.dto.request;

import com.example.gujeuck_server.domain.common.domain.funnel.enums.CheckInFunnelEventName;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.Instant;
import java.util.UUID;

public record CheckInFunnelEventRequest(
        @NotNull(message = "clientEventId를 입력해주세요.")
        UUID clientEventId,

        @NotNull(message = "sessionId를 입력해주세요.")
        UUID sessionId,

        @NotNull(message = "eventName을 입력해주세요.")
        CheckInFunnelEventName eventName,

        @NotNull(message = "occurredAt을 입력해주세요.")
        Instant occurredAt,

        @NotNull(message = "elapsedMsFromStart를 입력해주세요.")
        @PositiveOrZero(message = "elapsedMsFromStart는 0 이상이어야 합니다.")
        Long elapsedMsFromStart,

        Long userId,
        Age ageGroup,

        @Size(max = 255, message = "purpose는 255자 이하여야 합니다.")
        String purpose,

        @Size(max = 255, message = "failureReason은 255자 이하여야 합니다.")
        String failureReason
) {
}
