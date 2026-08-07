package com.example.gujeuck_server.domain.common.funnel.service;

import com.example.gujeuck_server.domain.common.funnel.domain.enums.VisitCountBucket;
import com.example.gujeuck_server.domain.common.funnel.domain.repository.CheckInFunnelEventRepository;
import com.example.gujeuck_server.domain.common.funnel.presentation.dto.request.CheckInFunnelEventRequest;
import com.example.gujeuck_server.domain.common.funnel.presentation.dto.request.CheckInFunnelEventsRequest;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.exception.UserAccessDeniedException;
import com.example.gujeuck_server.global.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateCheckInFunnelEventService {
    private final CheckInFunnelEventRepository checkInFunnelEventRepository;
    private final LogRepository logRepository;
    private final UserRepository userRepository;

    @Transactional
    public void execute(Long organId, CheckInFunnelEventsRequest request) {
        List<CheckInFunnelEventRequest> uniqueEvents = uniqueByClientEventId(request.events());
        if (uniqueEvents.isEmpty()) {
            return;
        }

        validateUserOwnership(organId, uniqueEvents);

        Map<Long, Long> visitCountsByUserId = uniqueEvents.stream()
                .map(CheckInFunnelEventRequest::userId)
                .filter(userId -> userId != null)
                .distinct()
                .collect(Collectors.toMap(Function.identity(), logRepository::countByUserId));

        uniqueEvents.forEach(event -> insertKeepingExistingClientEvent(event, visitCountsByUserId));
    }

    private List<CheckInFunnelEventRequest> uniqueByClientEventId(List<CheckInFunnelEventRequest> events) {
        Set<UUID> seenClientEventIds = new HashSet<>();

        return events.stream()
                .filter(event -> seenClientEventIds.add(event.clientEventId()))
                .toList();
    }

    private void validateUserOwnership(Long organId, List<CheckInFunnelEventRequest> events) {
        boolean hasInvalidUser = events.stream()
                .map(CheckInFunnelEventRequest::userId)
                .filter(userId -> userId != null)
                .distinct()
                .anyMatch(userId -> !userRepository.existsByIdAndOrganId(userId, organId));

        if (hasInvalidUser) {
            throw UserAccessDeniedException.EXCEPTION;
        }
    }

    private void insertKeepingExistingClientEvent(CheckInFunnelEventRequest request, Map<Long, Long> visitCountsByUserId) {
        Long visitCount = request.userId() == null ? null : visitCountsByUserId.get(request.userId());
        VisitCountBucket visitCountBucket = visitCount == null ? null : bucketOf(visitCount);

        checkInFunnelEventRepository.insertKeepingExistingClientEvent(
                request.clientEventId().toString(),
                request.sessionId().toString(),
                request.eventName().value(),
                TimeProvider.toKoreaLocalDateTime(request.occurredAt()),
                request.elapsedMsFromStart(),
                request.userId(),
                request.ageGroup() == null ? null : request.ageGroup().name(),
                request.purpose(),
                request.failureReason(),
                visitCount == null ? null : visitCount >= 2,
                visitCount,
                visitCountBucket == null ? null : visitCountBucket.name()
        );
    }

    private VisitCountBucket bucketOf(long visitCount) {
        if (visitCount <= 1) {
            return VisitCountBucket.FIRST_VISIT;
        }

        if (visitCount <= 3) {
            return VisitCountBucket.RETURNING_2_3;
        }

        if (visitCount <= 9) {
            return VisitCountBucket.RETURNING_4_9;
        }

        return VisitCountBucket.RETURNING_10_PLUS;
    }
}
