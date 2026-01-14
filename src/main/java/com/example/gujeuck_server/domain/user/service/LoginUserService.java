package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.exception.DuplicateLogException;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.request.LoginRequest;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.exception.UserNotFoundException;
import com.example.gujeuck_server.global.utility.DateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginUserService {

    private final UserRepository userRepository;
    private final LogRepository logRepository;

    private static final String TIME = "HH:mm";

    @Transactional
    public void login(LoginRequest request) {

        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        user.increaseCount();

        String formattedDate = DateFormatter.LocalDateForm(LocalDate.now());;

        String visitTime = LocalTime.now().format(DateTimeFormatter.ofPattern(TIME));;

        int currentYear = LocalDate.now().getYear();

        List<Log> logs = new ArrayList<>();

        logs.add(createUserLog(user, request, formattedDate, visitTime, currentYear));

        logRepository.saveAll(logs);
    }

    private Log createUserLog(
            User user,
            LoginRequest request,
            String date,
            String time,
            int currentYear
    ) {
        return Log.builder()
                .user(user)
                .name(user.getName())
                .age(user.getAge())
                .phone(user.getPhone())
                .maleCount(request.getMaleCount())
                .femaleCount(request.getFemaleCount())
                .privacyAgreed(user.isPrivacyAgreed())
                .purpose(request.getPurpose())
                .visitDate(date)
                .visitTime(time)
                .year(currentYear)
                .residence(user.getResidence())
                .build();
    }
}
