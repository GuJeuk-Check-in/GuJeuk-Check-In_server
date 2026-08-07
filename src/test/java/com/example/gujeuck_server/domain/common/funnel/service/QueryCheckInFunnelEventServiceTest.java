package com.example.gujeuck_server.domain.common.funnel.service;

import com.example.gujeuck_server.domain.common.funnel.domain.CheckInFunnelEvent;
import com.example.gujeuck_server.domain.common.funnel.domain.enums.VisitCountBucket;
import com.example.gujeuck_server.domain.common.funnel.domain.repository.CheckInFunnelEventRepository;
import com.example.gujeuck_server.domain.common.funnel.presentation.dto.response.CheckInFunnelEventResponse;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryCheckInFunnelEventServiceTest {

    @Mock
    private CheckInFunnelEventRepository checkInFunnelEventRepository;

    @InjectMocks
    private QueryCheckInFunnelEventService queryCheckInFunnelEventService;

    @Test
    void 체크인_퍼널_이벤트_원본을_최신순으로_조회한다() {
        PageRequest pageable = PageRequest.of(0, 30);
        CheckInFunnelEvent event = CheckInFunnelEvent.builder()
                .clientEventId("11111111-1111-1111-1111-111111111111")
                .sessionId("22222222-2222-2222-2222-222222222222")
                .eventName("check_in_completed_view")
                .occurredAt(LocalDateTime.of(2026, 8, 5, 19, 17, 30))
                .elapsedMsFromStart(120000L)
                .userId(123L)
                .ageGroup(Age.AGE_14_16)
                .purpose("친친마루(게임, 독서 등)")
                .isExistingUser(true)
                .visitCount(4L)
                .visitCountBucket(VisitCountBucket.RETURNING_4_9)
                .createdAt(LocalDateTime.of(2026, 8, 5, 19, 18, 0))
                .build();
        ReflectionTestUtils.setField(event, "id", 1L);

        when(checkInFunnelEventRepository.findAllByOrderByCreatedAtDescIdDesc(pageable))
                .thenReturn(new SliceImpl<>(List.of(event), pageable, false));

        Slice<CheckInFunnelEventResponse> response = queryCheckInFunnelEventService.execute(pageable);

        assertThat(response.getContent()).hasSize(1);
        CheckInFunnelEventResponse eventResponse = response.getContent().get(0);
        assertThat(eventResponse.id()).isEqualTo(1L);
        assertThat(eventResponse.clientEventId()).isEqualTo("11111111-1111-1111-1111-111111111111");
        assertThat(eventResponse.eventName()).isEqualTo("check_in_completed_view");
        assertThat(eventResponse.occurredAt()).isEqualTo(LocalDateTime.of(2026, 8, 5, 19, 17, 30));
        assertThat(eventResponse.visitCountBucket()).isEqualTo(VisitCountBucket.RETURNING_4_9);
        verify(checkInFunnelEventRepository).findAllByOrderByCreatedAtDescIdDesc(pageable);
    }
}
