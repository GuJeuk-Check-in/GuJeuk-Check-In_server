package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.log.entity.Log;
import com.example.gujeuck_server.domain.log.repository.LogRepository;
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.PurposeNotFoundException;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;
import com.example.gujeuck_server.domain.user.entity.User;
import com.example.gujeuck_server.domain.user.entity.enums.Age;
import com.example.gujeuck_server.domain.user.exception.ExistUserIdException;
import com.example.gujeuck_server.domain.user.repository.UserRepository;
import com.example.gujeuck_server.domain.user.dto.request.SignupRequest;
import com.example.gujeuck_server.global.utility.CalculateAgeService;
import com.example.gujeuck_server.global.utility.DateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final UserRepository userRepository;
    private final CalculateAgeService calculateAgeService;
    private final LogRepository logRepository;
    private final PurposeRepository purposeRepository;

    private static final String TIME = "HH:mm";

    @Transactional
    public void signup(SignupRequest request) {
        String userId = User.generateUserId(request.getName(), request.getBirthYMD());

        if (userRepository.existsByUserId(userId)) {
            throw ExistUserIdException.EXCEPTION;
        }

        Age age = calculateAgeService.getAge(request.getBirthYMD()); // 나이 변환기

        String formattedDate = DateFormatter.LocalDateForm(LocalDate.now()); // 객체 생성 시간에 맞게 날짜 설정

        String visitTime = LocalTime.now().format(DateTimeFormatter.ofPattern(TIME)); // 객체 생성 시간에 맞게 시간 설정

        int currentYear = LocalDate.now().getYear(); // 현재 연도 반환

        Purpose purpose = purposeRepository.findByPurpose(request.getPurpose())
                        .orElseThrow(() -> PurposeNotFoundException.EXCEPTION);

        userRepository.save(User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .gender(request.getGender())
                .birthYMD(request.getBirthYMD())
                .residence(request.getResidence())
                .privacyAgreed(request.isPrivacyAgreed())
                .age(age)
                .userId(userId)
                .build());

        logRepository.save(Log.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .age(age)
                .purpose(purpose.getPurpose())
                .privacyAgreed(request.isPrivacyAgreed())
                .visitDate(formattedDate)
                .year(currentYear)
                .visitTime(visitTime)
                .build());
    }
}
