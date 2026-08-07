package com.example.gujeuck_server.domain.common.funnel.presentation.dto.response;

import com.example.gujeuck_server.domain.common.funnel.domain.CheckInFunnelEvent;
import com.example.gujeuck_server.domain.common.funnel.domain.enums.VisitCountBucket;
import com.example.gujeuck_server.domain.user.domain.enums.Age;

import java.time.LocalDateTime;

public record CheckInFunnelEventResponse(
        Long id,
        String clientEventId,
        String sessionId,
        String eventName,
        LocalDateTime occurredAt,
        long elapsedMsFromStart,
        Long userId,
        Age ageGroup,
        String purpose,
        String failureReason,
        Boolean isExistingUser,
        Long visitCount,
        VisitCountBucket visitCountBucket,
        LocalDateTime createdAt
) {
    public CheckInFunnelEventResponse(CheckInFunnelEvent event) {
        this(
                event.getId(),
                event.getClientEventId(),
                event.getSessionId(),
                event.getEventName(),
                event.getOccurredAt(),
                event.getElapsedMsFromStart(),
                event.getUserId(),
                event.getAgeGroup(),
                event.getPurpose(),
                event.getFailureReason(),
                event.getIsExistingUser(),
                event.getVisitCount(),
                event.getVisitCountBucket(),
                event.getCreatedAt()
        );
    }
}
