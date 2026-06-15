package com.example.gujeuck_server.domain.organ.service;

import com.example.gujeuck_server.domain.log.domain.VisitStatisticsCount;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.exception.InvalidLogDateException;
import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.VisitStatisticsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryVisitStatisticsServiceTest {

    @Mock
    private LogRepository logRepository;

    @Mock
    private OrganFacade organFacade;

    @InjectMocks
    private QueryVisitStatisticsService queryVisitStatisticsService;

    private Organ organ;

    @BeforeEach
    void setUp() {
        organ = Organ.builder()
                .organName("구즉청소년문화의집")
                .password("password")
                .build();
        ReflectionTestUtils.setField(organ, "id", 1L);
    }

    @Test
    void 선택한_월과_연간_누계_통계를_기관별로_조회한다() {
        VisitStatisticsCount monthly = new VisitStatisticsCount(902, 654, 105, 99);
        VisitStatisticsCount cumulative = new VisitStatisticsCount(3558, 2654, 183, 154);

        when(organFacade.currentOrgan()).thenReturn(organ);
        when(logRepository.summarizeVisits(1L, "2026년04월01일", "2026년04월30일"))
                .thenReturn(monthly);
        when(logRepository.summarizeVisits(1L, "2026년01월01일", "2026년04월30일"))
                .thenReturn(cumulative);

        VisitStatisticsResponse response = queryVisitStatisticsService.execute(2026, 4);

        assertThat(response.year()).isEqualTo(2026);
        assertThat(response.month()).isEqualTo(4);
        assertThat(response.monthly().total()).isEqualTo(1760);
        assertThat(response.monthly().youth().total()).isEqualTo(1556);
        assertThat(response.monthly().youth().rate()).isEqualTo(88.4);
        assertThat(response.monthly().other().total()).isEqualTo(204);
        assertThat(response.monthly().other().rate()).isEqualTo(11.6);
        assertThat(response.cumulative().total()).isEqualTo(6549);
        assertThat(response.cumulative().youth().rate()).isEqualTo(94.9);
        assertThat(response.cumulative().other().rate()).isEqualTo(5.1);

        verify(logRepository).summarizeVisits(1L, "2026년04월01일", "2026년04월30일");
        verify(logRepository).summarizeVisits(1L, "2026년01월01일", "2026년04월30일");
    }

    @Test
    void 방문_기록이_없으면_모든_통계는_0이다() {
        VisitStatisticsCount empty = new VisitStatisticsCount(0, 0, 0, 0);

        when(organFacade.currentOrgan()).thenReturn(organ);
        when(logRepository.summarizeVisits(1L, "2026년01월01일", "2026년01월31일"))
                .thenReturn(empty);

        VisitStatisticsResponse response = queryVisitStatisticsService.execute(2026, 1);

        assertThat(response.monthly().total()).isZero();
        assertThat(response.monthly().youth().rate()).isZero();
        assertThat(response.monthly().other().rate()).isZero();
        assertThat(response.cumulative().total()).isZero();
    }

    @Test
    void 유효하지_않거나_미래인_월은_조회할_수_없다() {
        assertThatThrownBy(() -> queryVisitStatisticsService.execute(0, 1))
                .isSameAs(InvalidLogDateException.EXCEPTION);
        assertThatThrownBy(() -> queryVisitStatisticsService.execute(2026, 13))
                .isSameAs(InvalidLogDateException.EXCEPTION);
        assertThatThrownBy(() -> queryVisitStatisticsService.execute(2999, 1))
                .isSameAs(InvalidLogDateException.EXCEPTION);
    }
}
