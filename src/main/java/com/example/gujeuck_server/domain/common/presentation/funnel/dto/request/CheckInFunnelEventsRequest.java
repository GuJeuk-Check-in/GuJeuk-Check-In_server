package com.example.gujeuck_server.domain.common.presentation.funnel.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CheckInFunnelEventsRequest(
        @NotNull(message = "events를 입력해주세요.")
        @Size(min = 1, max = 100, message = "events는 1개 이상 100개 이하로 전송해주세요.")
        List<@NotNull(message = "event는 null일 수 없습니다.") @Valid CheckInFunnelEventRequest> events
) {
}
