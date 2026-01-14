package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.domain.user.exception.ExistUserIdException;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.request.SignupRequest;
import com.example.gujeuck_server.domain.user.presentation.dto.response.SignupResponse;
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
    private final PurposeFacade purposeFacade;

    private static final String TIME = "HH:mm";

    @Transactional
    public SignupResponse signup(SignupRequest request) {

        SignupResponse signupResponse = createUserId(request.getName(), request.getBirthYMD());

        Age age = calculateAgeService.getAge(request.getBirthYMD());

        String visitDate = DateFormatter.LocalDateForm(LocalDate.now());;

        String visitTime = LocalTime.now().format(DateTimeFormatter.ofPattern(TIME));

        int currentYear = LocalDate.now().getYear();

        Purpose purpose = purposeFacade.getPurpose(request.getPurpose());

        String resolvedResidence = User.resolveResidence(request.getResidence());;

        User user = createUser(request, age, signupResponse.userId(), resolvedResidence);

        user.increaseCount();

        userRepository.save(user);

        Log log = createLog(request, age, purpose, visitDate, visitTime, currentYear, resolvedResidence);

        logRepository.save(log);

        return signupResponse;
    }

    private SignupResponse createUserId(String name, String birthYMD) {

        String userId = User.generateUserId(name, birthYMD);

        if (userRepository.findByUserId(userId).isPresent()) {
            throw ExistUserIdException.EXCEPTION;
        }

        return SignupResponse.builder()
                .userId(userId)
                .build();
    }

    private User createUser(SignupRequest request, Age age, String userId, String residence) {

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

    private Log createLog(SignupRequest request, Age age, Purpose purpose, String visitDate, String visitTime, int year, String residence) {

        return Log.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .age(age)
                .maleCount(request.getMaleCount())
                .femaleCount(request.getFemaleCount())
                .purpose(purpose.getPurposeName())
                .privacyAgreed(request.isPrivacyAgreed())
                .visitDate(visitDate)
                .visitTime(visitTime)
                .year(year)
                .residence(residence)
                .build();
    }
}

