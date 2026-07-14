package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.exception.DuplicateLogException;
import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.domain.user.domain.enums.Gender;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.request.UserCheckInRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCheckInServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LogRepository logRepository;

    @Mock
    private PurposeFacade purposeFacade;

    @InjectMocks
    private UserCheckInService userCheckInService;

    @Test
    void 같은_분에_이미_방문한_사용자는_중복_로그를_만들지_않는다() {
        UserCheckInRequest request = checkInRequest();
        User user = user();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(purposeFacade.getPurpose(anyLong(), anyString()))
                .thenReturn(Purpose.builder().purposeName("테스트").build());
        when(logRepository.findByUserIdAndVisitTime(anyLong(), anyString(), anyString()))
                .thenReturn(Optional.of(Log.builder().build()));

        assertThatThrownBy(() -> userCheckInService.execute(request))
                .isSameAs(DuplicateLogException.EXCEPTION);

        verify(logRepository, never()).save(org.mockito.ArgumentMatchers.any(Log.class));
    }

    private UserCheckInRequest checkInRequest() {
        UserCheckInRequest request = new UserCheckInRequest();
        ReflectionTestUtils.setField(request, "userId", 1L);
        ReflectionTestUtils.setField(request, "purpose", "테스트");
        ReflectionTestUtils.setField(request, "maleCount", 0);
        ReflectionTestUtils.setField(request, "femaleCount", 0);
        ReflectionTestUtils.setField(request, "visitTime", java.time.LocalDateTime.of(2026, 7, 14, 14, 30));
        return request;
    }

    private User user() {
        Organ organ = Organ.builder().build();
        ReflectionTestUtils.setField(organ, "id", 1L);

        User user = User.builder()
                .name("테스터")
                .phone("01000000000")
                .privacyAgreed(true)
                .gender(Gender.MAN)
                .residence("테스트")
                .age(Age.ADULT)
                .birthYMD("2000-01-01")
                .count(1)
                .organ(organ)
                .build();
        ReflectionTestUtils.setField(user, "id", 1L);
        return user;
    }
}
