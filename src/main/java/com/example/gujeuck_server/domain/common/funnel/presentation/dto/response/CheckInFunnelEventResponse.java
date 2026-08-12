package com.example.gujeuck_server.domain.common.funnel.presentation.dto.response;

import com.example.gujeuck_server.domain.common.funnel.domain.CheckInFunnelEvent;
import com.example.gujeuck_server.domain.common.funnel.domain.enums.VisitCountBucket;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
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
    public static CheckInFunnelEventResponse from(CheckInFunnelEvent event) {
        return CheckInFunnelEventResponse.builder()
                .id(event.getId())
                .clientEventId(event.getClientEventId())
                .sessionId(event.getSessionId())
                .eventName(event.getEventName())
                .occurredAt(event.getOccurredAt())
                .elapsedMsFromStart(event.getElapsedMsFromStart())
                .userId(event.getUserId())
                .ageGroup(event.getAgeGroup())
                .purpose(event.getPurpose())
                .failureReason(event.getFailureReason())
                .isExistingUser(event.getIsExistingUser())
                .visitCount(event.getVisitCount())
                .visitCountBucket(event.getVisitCountBucket())
                .createdAt(event.getCreatedAt())
                .build();
    }
}
