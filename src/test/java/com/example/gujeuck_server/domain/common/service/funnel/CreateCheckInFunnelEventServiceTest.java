package com.example.gujeuck_server.domain.common.service.funnel;

import com.example.gujeuck_server.domain.common.domain.funnel.enums.CheckInFunnelEventName;
import com.example.gujeuck_server.domain.common.domain.funnel.repository.CheckInFunnelEventRepository;
import com.example.gujeuck_server.domain.common.presentation.funnel.dto.request.CheckInFunnelEventRequest;
import com.example.gujeuck_server.domain.common.presentation.funnel.dto.request.CheckInFunnelEventsRequest;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCheckInFunnelEventServiceTest {

    private static final LocalDateTime OCCURRED_AT = LocalDateTime.of(2026, 8, 5, 19, 17, 30);

    @Mock
    private CheckInFunnelEventRepository checkInFunnelEventRepository;

    @Mock
    private LogRepository logRepository;

    @InjectMocks
    private CreateCheckInFunnelEventService createCheckInFunnelEventService;

    @Test
    void 요청_내_중복_clientEventId는_한_번만_insert를_시도한다() {
        UUID newClientEventId = UUID.fromString("22222222-2222-2222-2222-222222222222");

        when(logRepository.countByUserId(10L)).thenReturn(4L);

        createCheckInFunnelEventService.execute(new CheckInFunnelEventsRequest(List.of(
                event(newClientEventId, 10L),
                event(newClientEventId, 10L)
        )));

        verifyInserted(newClientEventId, 10L, true, 4L, "RETURNING_4_9");
    }

    @Test
    void userId가_없으면_방문횟수_계산값을_null로_저장한다() {
        UUID clientEventId = UUID.fromString("33333333-3333-3333-3333-333333333333");

        createCheckInFunnelEventService.execute(new CheckInFunnelEventsRequest(List.of(
                event(clientEventId, null)
        )));

        verify(logRepository, never()).countByUserId(any());
        verifyInserted(clientEventId, null, null, null, null);
    }

    @Test
    void 방문횟수별_버킷을_계산한다() {
        UUID firstVisitClientEventId = UUID.fromString("44444444-4444-4444-4444-444444444444");
        UUID returningClientEventId = UUID.fromString("55555555-5555-5555-5555-555555555555");
        UUID loyalClientEventId = UUID.fromString("66666666-6666-6666-6666-666666666666");

        when(logRepository.countByUserId(1L)).thenReturn(1L);
        when(logRepository.countByUserId(2L)).thenReturn(3L);
        when(logRepository.countByUserId(3L)).thenReturn(10L);

        createCheckInFunnelEventService.execute(new CheckInFunnelEventsRequest(List.of(
                event(firstVisitClientEventId, 1L),
                event(returningClientEventId, 2L),
                event(loyalClientEventId, 3L)
        )));

        verifyInserted(firstVisitClientEventId, 1L, false, 1L, "FIRST_VISIT");
        verifyInserted(returningClientEventId, 2L, true, 3L, "RETURNING_2_3");
        verifyInserted(loyalClientEventId, 3L, true, 10L, "RETURNING_10_PLUS");
    }

    @Test
    void 이미_저장된_clientEventId는_repository의_noop_upsert로_성공_처리한다() {
        UUID duplicateClientEventId = UUID.fromString("77777777-7777-7777-7777-777777777777");

        createCheckInFunnelEventService.execute(new CheckInFunnelEventsRequest(List.of(
                event(duplicateClientEventId, null)
        )));

        verifyInserted(duplicateClientEventId, null, null, null, null);
    }

    private CheckInFunnelEventRequest event(UUID clientEventId, Long userId) {
        return new CheckInFunnelEventRequest(
                clientEventId,
                UUID.fromString("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"),
                CheckInFunnelEventName.CHECK_IN_COMPLETED_VIEW,
                Instant.parse("2026-08-05T10:17:30.000Z"),
                120000L,
                userId,
                Age.AGE_14_16,
                "친친마루(게임, 독서 등)",
                null
        );
    }

    private void verifyInserted(
            UUID clientEventId,
            Long userId,
            Boolean isExistingUser,
            Long visitCount,
            String visitCountBucket
    ) {
        verify(checkInFunnelEventRepository).insertKeepingExistingClientEvent(
                eq(clientEventId.toString()),
                anyString(),
                anyString(),
                eq(OCCURRED_AT),
                anyLong(),
                userId == null ? isNull() : eq(userId),
                anyString(),
                anyString(),
                isNull(),
                isExistingUser == null ? isNull() : eq(isExistingUser),
                visitCount == null ? isNull() : eq(visitCount),
                visitCountBucket == null ? isNull() : eq(visitCountBucket)
        );
    }
}
