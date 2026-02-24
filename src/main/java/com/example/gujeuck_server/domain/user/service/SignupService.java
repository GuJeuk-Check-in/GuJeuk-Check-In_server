package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
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
    private final OrganFacade organFacade;

    @Transactional
    public SignUpResponse execute(SignupRequest request) {

        Organ organ = organFacade.currentOrgan();

        SignUpResponse signupResponse = createUserId(organ.getId(), request.getName(), request.getBirthYMD());

        Age age = calculateAgeService.getAge(request.getBirthYMD());

        String visitDate = TimeProvider.nowDateFormatted();

        String visitTime = TimeProvider.nowTimeFormatted();

        int currentYear = TimeProvider.nowYear();

        Purpose purpose = purposeFacade.getPurpose(organ.getId(), request.getPurpose());

        String resolvedResidence = User.resolveResidence(request.getResidence());;

        User user = createUser(request, age, signupResponse.getUserId(), resolvedResidence, organ);

        user.increaseCount();

        userRepository.save(user);

        Log log = createLog(request, age, purpose, visitDate, visitTime, currentYear, resolvedResidence, user, organ);

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
                .residence(residence)
                .organ(organ)
                .build();
    }
}
