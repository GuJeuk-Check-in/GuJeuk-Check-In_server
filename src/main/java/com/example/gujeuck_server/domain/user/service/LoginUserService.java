package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.request.LoginRequest;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.exception.UserNotFoundException;
import com.example.gujeuck_server.global.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginUserService {

    private final UserRepository userRepository;
    private final LogRepository logRepository;

    @Transactional
    public void execute(LoginRequest request) {

        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        user.increaseCount();

        String visitDate = TimeProvider.nowDateFormatted();

        String visitTime = TimeProvider.nowTimeFormatted();

        int currentYear = TimeProvider.nowYear();

        Log log = createUserLog(user, request, visitDate, visitTime, currentYear);

        logRepository.save(log);
    }

    private Log createUserLog(User user, LoginRequest request, String visitDate, String visitTime, int currentYear) {

        return Log.builder()
                .user(user)
                .name(user.getName())
                .age(user.getAge())
                .phone(user.getPhone())
                .maleCount(request.getMaleCount())
                .femaleCount(request.getFemaleCount())
                .privacyAgreed(user.isPrivacyAgreed())
                .purpose(request.getPurpose())
                .visitDate(visitDate)
                .visitTime(visitTime)
                .year(currentYear)
                .organ(user.getOrgan())
                .build();
    }
}
