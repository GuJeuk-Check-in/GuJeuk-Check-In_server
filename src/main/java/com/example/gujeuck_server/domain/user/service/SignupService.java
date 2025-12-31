package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.log.entity.Log;
import com.example.gujeuck_server.domain.log.repository.LogRepository;
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.PurposeNotFoundException;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;
import com.example.gujeuck_server.domain.user.dto.response.SignUpResponse;
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

        Purpose purpose = findPurpose(request.getPurpose());
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

    private Purpose findPurpose(String purposeName) {
        List<Purpose> list = purposeRepository.findByPurpose(purposeName);

        if (list.isEmpty()) {
            throw PurposeNotFoundException.EXCEPTION;
        }
        return list.get(0);
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
                .purpose(purpose.getPurpose())
                .privacyAgreed(request.isPrivacyAgreed())
                .visitDate(date)
                .visitTime(time)
                .year(year)
                .residence(residence)
                .build();
    }
}

