package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.log.entity.Log;
import com.example.gujeuck_server.domain.log.repository.LogRepository;
import com.example.gujeuck_server.domain.user.dto.request.LoginRequest;
import com.example.gujeuck_server.domain.user.entity.User;
import com.example.gujeuck_server.domain.user.exception.CompanionIdNotFoundException;
import com.example.gujeuck_server.domain.user.repository.UserRepository;
import com.example.gujeuck_server.domain.user.exception.UserNotFoundException;
import com.example.gujeuck_server.global.security.jwt.JwtTokenProvider;
import com.example.gujeuck_server.global.utility.DateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final LogRepository logRepository;

    @Transactional
    public void login(LoginRequest request) {

        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(()-> UserNotFoundException.EXCEPTION);

        String formattedDate = DateFormatter.LocalDateForm(LocalDate.now());

        List<Log> logs = new ArrayList<>();

        logs.add(Log.builder()
                .name(user.getName())
                .age(user.getAge())
                .phone(user.getPhone())
                .privacyAgreed(user.isPrivacyAgreed())
                .purpose(request.getPurpose())
                .visitDate(formattedDate)
                .build());

        if (request.getCompanionIds() != null && !request.getCompanionIds().isEmpty()) {
            for (String companionId : request.getCompanionIds()) {

                User companion = userRepository.findByUserId(companionId)
                        .orElseThrow(() -> CompanionIdNotFoundException.of(companionId));

                logs.add(Log.builder()
                        .name(companion.getName())
                        .age(companion.getAge())
                        .phone(companion.getPhone())
                        .privacyAgreed(companion.isPrivacyAgreed())
                        .purpose(request.getPurpose())
                        .visitDate(formattedDate)
                        .build());
            }
        }

        logRepository.saveAll(logs);
    }
}
