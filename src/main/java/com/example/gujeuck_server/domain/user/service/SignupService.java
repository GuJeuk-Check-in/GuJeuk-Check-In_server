package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.domain.repository.OrganRepository;
import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import com.example.gujeuck_server.domain.residence.domain.repository.ResidenceRepository;
import com.example.gujeuck_server.domain.residence.exception.ResidenceNotFoundException;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.request.SignupRequest;
import com.example.gujeuck_server.global.utility.CalculateAgeService;
import com.example.gujeuck_server.global.utility.DateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserRepository userRepository;
    private final CalculateAgeService calculateAgeService;
    private final LogRepository logRepository;
    private final OrganRepository organRepository;
    private final ResidenceRepository residenceRepository;
    private final PurposeFacade purposeFacade;

    private static final Long HARDCODED_ORGAN_ID = 1L;
    private static final DateTimeFormatter VISIT_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @Transactional
    public void execute(SignupRequest request) {

        Organ organ = organRepository.findById(HARDCODED_ORGAN_ID)
                .orElseThrow(() -> new RuntimeException("Organ not found"));

        Age age = calculateAgeService.getAge(request.getBirthYMD());

        LocalDateTime visitDateTime = request.getVisitTime();

        String visitDate = DateFormatter.LocalDateForm(visitDateTime.toLocalDate());

        String visitTime = visitDateTime.toLocalTime().format(VISIT_TIME_FORMATTER);

        int currentYear = visitDateTime.getYear();

        residenceRepository.findByOrganIdAndResidenceName(organ.getId(), request.getResidence())
                .orElseThrow(() -> ResidenceNotFoundException.EXCEPTION);

        Purpose purpose = purposeFacade.getPurpose(organ.getId(), request.getPurpose());

        User user = userRepository.findByNameAndPhone(request.getName(), request.getPhone())
                .orElseGet(() -> userRepository.save(createUser(request, age, request.getResidence(), organ)));

        // 신규/기존 유저 모두 방문 횟수를 증가시켜 로그 수와 일치시킨다.
        user.increaseCount();

        Log log = createLog(request, age, purpose.getPurposeName(), visitDate, visitTime, currentYear, request.getResidence(), user, organ);

        logRepository.save(log);
    }

    private User createUser(SignupRequest request, Age age, String residence, Organ organ) {

        return User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .gender(request.getGender())
                .birthYMD(request.getBirthYMD())
                .residence(residence)
                .privacyAgreed(request.getPrivacyAgreed())
                .age(age)
                .organ(organ)
                .build();
    }

    private Log createLog(SignupRequest request, Age age, String purpose, String visitDate, String visitTime, int year, String residence, User user, Organ organ) {
        return Log.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .age(age)
                .maleCount(request.getMaleCount())
                .femaleCount(request.getFemaleCount())
                .purpose(purpose)
                .privacyAgreed(request.getPrivacyAgreed())
                .visitDate(visitDate)
                .visitTime(visitTime)
                .year(year)
                .user(user)
                .organ(organ)
                .build();
    }
}
