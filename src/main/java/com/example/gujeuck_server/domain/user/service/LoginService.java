package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.log.entity.Log;
import com.example.gujeuck_server.domain.log.exception.DuplicateLogException;
import com.example.gujeuck_server.domain.log.repository.LogRepository;
import com.example.gujeuck_server.domain.user.dto.request.LoginRequest;
import com.example.gujeuck_server.domain.user.entity.User;
import com.example.gujeuck_server.domain.user.repository.UserRepository;
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
public class LoginService {

    private final UserRepository userRepository;
    private final LogRepository logRepository;

    private static final String TIME = "HH:mm";

    @Transactional
    public void login(LoginRequest request) {

        User user = findUser(request.getUserId());
        user.increaseCount();

        String formattedDate = getFormattedDate();
        String visitTime = getVisitTime();
        int currentYear = getCurrentYear();

        validateDuplicateLog(user.getUserId(), formattedDate, visitTime);

        List<Log> logs = new ArrayList<>();

        logs.add(createUserLog(user, request, formattedDate, visitTime, currentYear));
        
        logRepository.saveAll(logs);
    }

    private User findUser(String userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    private String getFormattedDate() {
        return DateFormatter.LocalDateForm(LocalDate.now());
    }

    private String getVisitTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern(TIME));
    }

    private int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    private void validateDuplicateLog(String userId, String date, String time) {
        if (logRepository.findByUserIdAndVisitTime(userId, date, time).isPresent()) {
            throw DuplicateLogException.EXCEPTION;
        }
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
