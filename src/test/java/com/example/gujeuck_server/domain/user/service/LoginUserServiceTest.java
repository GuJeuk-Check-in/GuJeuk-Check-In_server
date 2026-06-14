package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.exception.DuplicateLogException;
import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.domain.user.domain.enums.Gender;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private LogRepository logRepository;

    @InjectMocks
    private LoginUserService loginUserService;

    @Test
    void 같은_분에_이미_방문한_사용자는_중복_로그를_만들지_않는다() {
        LoginRequest request = loginRequest();
        User user = user();

        when(userRepository.findByUserId("tester0101")).thenReturn(Optional.of(user));
        when(logRepository.findByUserIdAndVisitTime(anyString(), anyString(), anyString()))
                .thenReturn(Optional.of(Log.builder().build()));

        assertThatThrownBy(() -> loginUserService.execute(request))
                .isSameAs(DuplicateLogException.EXCEPTION);

        verify(logRepository, never()).saveAndFlush(org.mockito.ArgumentMatchers.any(Log.class));
    }

    private LoginRequest loginRequest() {
        LoginRequest request = new LoginRequest();
        ReflectionTestUtils.setField(request, "userId", "tester0101");
        ReflectionTestUtils.setField(request, "purpose", "테스트");
        ReflectionTestUtils.setField(request, "maleCount", 0);
        ReflectionTestUtils.setField(request, "femaleCount", 0);
        return request;
    }

    private User user() {
        return User.builder()
                .userId("tester0101")
                .name("테스터")
                .phone("01000000000")
                .privacyAgreed(true)
                .gender(Gender.MAN)
                .residence("테스트")
                .age(Age.ADULT)
                .birthYMD("2000-01-01")
                .count(1)
                .organ(Organ.builder().build())
                .build();
    }
}
