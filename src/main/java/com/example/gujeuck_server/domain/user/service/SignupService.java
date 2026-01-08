package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.PurposeNotFoundException;
import com.example.gujeuck_server.domain.purpose.domain.repository.PurposeRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.response.SignUpResponse;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.domain.user.exception.ExistUserIdException;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.request.SignupRequest;
import com.example.gujeuck_server.global.utility.CalculateAgeService;
import com.example.gujeuck_server.global.utility.DateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserRepository userRepository;
    private final CalculateAgeService calculateAgeService;
    private final LogRepository logRepository;
    private final PurposeRepository purposeRepository;

    private static final String TIME = "HH:mm";

    @Transactional
    public SignUpResponse signup(SignupRequest request) {

        String userId = generateUserId(request);
        SignUpResponse response = SignUpResponse.builder()
                .userId(userId)
                .build();
        validateDuplicateUserId(userId);

        Age age = calculateAge(request);
        String formattedDate = getFormattedDate();
        String visitTime = getVisitTime();
        int currentYear = getCurrentYear();

        Purpose purpose = purposeRepository.findByPurpose(request.getPurpose())
                .orElseThrow(() -> PurposeNotFoundException.EXCEPTION);

        String resolvedResidence = resolveResidence(request.getResidence());

        User user = createUserEntity(request, age, userId, resolvedResidence);
        user.increaseCount();
        userRepository.save(user);

        Log log = createSignupLog(request, age, purpose, formattedDate, visitTime, currentYear, resolvedResidence);
        logRepository.save(log);

        return response;
    }

    private String generateUserId(SignupRequest request) {
        return User.generateUserId(request.getName(), request.getBirthYMD());
    }

    private void validateDuplicateUserId(String userId) {
        if (userRepository.findByUserId(userId).isPresent()) {
            throw ExistUserIdException.EXCEPTION;
        }
    }

    private Age calculateAge(SignupRequest request) {
        return calculateAgeService.getAge(request.getBirthYMD());
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

    private String resolveResidence(String residence) {
        return User.resolveResidence(residence);
    }

    private User createUserEntity(SignupRequest request, Age age, String userId, String residence) {
        return User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .gender(request.getGender())
                .birthYMD(request.getBirthYMD())
                .residence(residence)
                .privacyAgreed(request.isPrivacyAgreed())
                .age(age)
                .userId(userId)
                .build();
    }

    private Log createSignupLog(
            SignupRequest request,
            Age age,
            Purpose purpose,
            String date,
            String time,
            int year,
            String residence
    ) {
        return Log.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .age(age)
                .maleCount(request.getMaleCount())
                .femaleCount(request.getFemaleCount())
                .purpose(purpose.getPurpose())
                .privacyAgreed(request.isPrivacyAgreed())
                .visitDate(date)
                .visitTime(time)
                .year(year)
                .residence(residence)
                .build();
    }
}

