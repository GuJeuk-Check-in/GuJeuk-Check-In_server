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

@Service
@RequiredArgsConstructor
public class SignupService {
    private final UserRepository userRepository;
    private final CalculateAgeService calculateAgeService;
    private final LogRepository logRepository;
    private final PurposeRepository purposeRepository;

    @Transactional
    public void signup(SignupRequest request) {
        String userId = User.generateUserId(request.getName(), request.getBirthYMD());

        if (userRepository.existsByUserId(userId)) {
            throw ExistUserIdException.EXCEPTION;
        }

        Age age = calculateAgeService.getAge(request.getBirthYMD());

        String formattedDate = DateFormatter.LocalDateForm(LocalDate.now());

        int currentYear = LocalDate.now().getYear();

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
                .build());
    }
}
