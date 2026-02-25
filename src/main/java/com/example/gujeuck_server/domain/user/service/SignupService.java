package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.domain.repository.OrganRepository;
import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.domain.user.exception.ExistUserIdException;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.request.SignupRequest;
import com.example.gujeuck_server.domain.user.presentation.dto.response.SignUpResponse;
import com.example.gujeuck_server.global.utility.CalculateAgeService;
import com.example.gujeuck_server.global.utility.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserRepository userRepository;
    private final CalculateAgeService calculateAgeService;
    private final LogRepository logRepository;
    private final PurposeFacade purposeFacade;
    private final OrganRepository organRepository;

    private static final Long HARDCODED_ORGAN_ID = 1L;

    @Transactional
    public SignUpResponse execute(SignupRequest request) {

        Organ organ = organRepository.findById(HARDCODED_ORGAN_ID)
                .orElseThrow(() -> new RuntimeException("Organ not found"));

        SignUpResponse signupResponse = createUserId(HARDCODED_ORGAN_ID, request.getName(), request.getBirthYMD());

        Age age = calculateAgeService.getAge(request.getBirthYMD());

        String visitDate = TimeProvider.nowDateFormatted();

        String visitTime = TimeProvider.nowTimeFormatted();

        int currentYear = TimeProvider.nowYear();

        Purpose purpose = purposeFacade.getPurpose(HARDCODED_ORGAN_ID, request.getPurpose());

        User user = createUser(request, age, signupResponse.getUserId(), request.getResidence(), organ);

        user.increaseCount();

        userRepository.save(user);

        Log log = createLog(request, age, purpose, visitDate, visitTime, currentYear, request.getResidence(), user, organ);

        logRepository.save(log);

        return signupResponse;
    }

    private SignUpResponse createUserId(Long organId, String name, String birthYMD) {

        String userId = User.generateUserId(name, birthYMD);

        if (userRepository.findByUserIdAndOrganId(userId, organId).isPresent()) {
            throw ExistUserIdException.EXCEPTION;
        }

        return SignUpResponse.builder()
                .userId(userId)
                .build();
    }

    private User createUser(SignupRequest request, Age age, String userId, String residence, Organ organ) {

        return User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .gender(request.getGender())
                .birthYMD(request.getBirthYMD())
                .residence(residence)
                .privacyAgreed(request.isPrivacyAgreed())
                .age(age)
                .userId(userId)
                .organ(organ)
                .build();
    }

    private Log createLog(SignupRequest request, Age age, Purpose purpose, String visitDate, String visitTime, int year, String residence, User user, Organ organ) {
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
                .user(user)
                .organ(organ)
                .build();
    }
}
