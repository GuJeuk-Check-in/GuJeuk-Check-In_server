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

    @Transactional
    public void execute(SignupRequest request) {

        Organ organ = organRepository.findById(HARDCODED_ORGAN_ID)
                .orElseThrow(() -> new RuntimeException("Organ not found"));

        Age age = calculateAgeService.getAge(request.birthYMD());

        LocalDateTime visitDateTime = request.visitTime();

        String visitDate = DateFormatter.toVisitDate(visitDateTime);

        String visitTime = DateFormatter.toVisitTime(visitDateTime);

        int currentYear = visitDateTime.getYear();

        residenceRepository.findByOrganIdAndResidenceName(organ.getId(), request.residence())
                .orElseThrow(() -> ResidenceNotFoundException.EXCEPTION);

        Purpose purpose = purposeFacade.getPurpose(organ.getId(), request.purpose());

        User user = userRepository.findAllByName(request.name()).stream()
                .findFirst()
                .orElseGet(() -> userRepository.save(createUser(request, age, request.residence(), organ)));

        user.increaseCount();

        Log log = createLog(request, age, purpose.getPurposeName(), visitDate, visitTime, visitDateTime, currentYear, request.residence(), user, organ);

        logRepository.save(log);
    }

    private User createUser(SignupRequest request, Age age, String residence, Organ organ) {

        return User.builder()
                .name(request.name())
                .phone(request.phone())
                .gender(request.gender())
                .birthYMD(request.birthYMD())
                .residence(residence)
                .privacyAgreed(request.privacyAgreed())
                .age(age)
                .organ(organ)
                .build();
    }

    private Log createLog(SignupRequest request, Age age, String purpose, String visitDate, String visitTime, LocalDateTime visitAt, int year, String residence, User user, Organ organ) {
        return Log.builder()
                .name(request.name())
                .phone(request.phone())
                .age(age)
                .maleCount(request.maleCount())
                .femaleCount(request.femaleCount())
                .purpose(purpose)
                .privacyAgreed(request.privacyAgreed())
                .visitDate(visitDate)
                .visitTime(visitTime)
                .visitAt(visitAt)
                .year(year)
                .user(user)
                .organ(organ)
                .build();
    }
}
