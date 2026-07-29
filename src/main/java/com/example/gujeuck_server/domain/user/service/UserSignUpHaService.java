package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.domain.repository.OrganRepository;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.request.HaDataSignUpRequest;
import com.example.gujeuck_server.domain.user.presentation.dto.request.UserCheckInRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSignUpHaService {

    private final UserRepository userRepository;
    private final OrganRepository organRepository;
    private final UserCheckInService userCheckInService;

    private static final Long HARDCODED_ORGAN_ID = 1L;

    @Transactional
    public void execute(List<HaDataSignUpRequest> requests) {
        Organ organ = organRepository.findById(HARDCODED_ORGAN_ID)
                .orElseThrow(() -> new RuntimeException("Organ not found"));

        for (HaDataSignUpRequest request : requests) {

            User user = findOrCreateUser(organ, request);

            UserCheckInRequest checkInRequest = UserCheckInRequest.create(
                user.getId(),
                request.maleCount(),
                request.femaleCount(),
                request.purpose(),
                request.visitTime()
            );

            userCheckInService.execute(checkInRequest);
        }
    }

    private User findOrCreateUser(Organ organ, HaDataSignUpRequest request) {

        String normalizedPhone = normalizePhone(request.phone());

        if (!normalizedPhone.isEmpty()) {
            List<User> foundUsers =
                userRepository.findAllByNameAndNormalizedPhone(
                    request.name(),
                    normalizedPhone
                );

            if (!foundUsers.isEmpty()) {
                return foundUsers.get(0);
            }
        }

        User newUser = User.builder()
            .name(request.name())
            .phone(request.phone())
            .age(request.age())
            .residence(request.residence())
            .birthYMD(request.birthYMD())
            .gender(request.gender())
            .privacyAgreed(request.privacyAgreed())
            .organ(organ)
            .build();

        return userRepository.save(newUser);
    }

    private String normalizePhone(String phone) {
        return phone == null ? "" : phone.replaceAll("\\D", "");
    }
}