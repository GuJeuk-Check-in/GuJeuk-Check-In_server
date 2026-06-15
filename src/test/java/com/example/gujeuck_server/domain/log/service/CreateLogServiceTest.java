package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.exception.DuplicateLogException;
import com.example.gujeuck_server.domain.log.presentation.dto.request.LogRequest;
import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateLogServiceTest {

    @Mock
    private LogRepository logRepository;

    @Mock
    private PurposeFacade purposeFacade;

    @InjectMocks
    private CreateLogService createLogService;

    @Test
    void 기관_범위에서_동일한_방문기록이_있으면_중복_예외를_던진다() {
        Organ organ = organ(1L, "테스트기관");
        LogRequest request = logRequest();

        when(purposeFacade.getPurpose(1L, "상담")).thenReturn(Purpose.builder().purposeName("상담").build());
        when(logRepository.existsByOrganIdAndNameAndAgeAndPurposeAndVisitDateAndVisitTime(
                1L,
                "홍길동",
                Age.ADULT,
                "상담",
                "2026년06월15일",
                "10:30"
        )).thenReturn(true);

        assertThatThrownBy(() -> createLogService.execute(organ, request))
                .isSameAs(DuplicateLogException.EXCEPTION);
    }

    @Test
    void 공개와_관리자_API가_같은_서비스로_기관별_방문기록을_생성한다() {
        Organ organ = organ(1L, "테스트기관");
        LogRequest request = logRequest();

        when(purposeFacade.getPurpose(1L, "상담")).thenReturn(Purpose.builder().purposeName("상담").build());
        when(logRepository.existsByOrganIdAndNameAndAgeAndPurposeAndVisitDateAndVisitTime(
                1L,
                "홍길동",
                Age.ADULT,
                "상담",
                "2026년06월15일",
                "10:30"
        )).thenReturn(false);

        createLogService.execute(organ, request);

        ArgumentCaptor<Log> captor = ArgumentCaptor.forClass(Log.class);
        verify(logRepository).save(captor.capture());

        Log saved = captor.getValue();
        assertThat(saved.getOrgan()).isSameAs(organ);
        assertThat(saved.getName()).isEqualTo("홍길동");
        assertThat(saved.getPhone()).isEqualTo("01012345678");
        assertThat(saved.getPurpose()).isEqualTo("상담");
        assertThat(saved.getVisitDate()).isEqualTo("2026년06월15일");
        assertThat(saved.getVisitTime()).isEqualTo("10:30");
    }

    private LogRequest logRequest() {
        LogRequest request = new LogRequest();
        ReflectionTestUtils.setField(request, "name", " 홍길동 ");
        ReflectionTestUtils.setField(request, "age", Age.ADULT);
        ReflectionTestUtils.setField(request, "phone", " 01012345678 ");
        ReflectionTestUtils.setField(request, "maleCount", 1);
        ReflectionTestUtils.setField(request, "femaleCount", 0);
        ReflectionTestUtils.setField(request, "purpose", " 상담 ");
        ReflectionTestUtils.setField(request, "visitDate", "2026-06-15");
        ReflectionTestUtils.setField(request, "visitTime", " 10:30 ");
        ReflectionTestUtils.setField(request, "privacyAgreed", true);
        ReflectionTestUtils.setField(request, "residence", "서울");
        return request;
    }

    private Organ organ(Long id, String organName) {
        Organ organ = Organ.builder()
                .organName(organName)
                .password("encoded-password")
                .build();
        ReflectionTestUtils.setField(organ, "id", id);
        return organ;
    }
}
