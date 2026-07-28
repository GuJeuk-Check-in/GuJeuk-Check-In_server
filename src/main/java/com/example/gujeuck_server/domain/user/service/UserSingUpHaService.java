package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.request.HaDataSignUpRequest;
import com.example.gujeuck_server.domain.user.presentation.dto.request.UserCheckInRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.gujeuck_server.domain.log.domain.QLog.log;

@Service
@RequiredArgsConstructor
public class UserSingUpHaService {
    private final UserRepository userRepository;
    private final LogRepository logRepository;

    @Transactional
    public void execute(List<HaDataSignUpRequest> requests) {
        for (HaDataSignUpRequest request : requests) {

            List<User> foundUsers =
                userRepository.findAllByNameAndNormalizedPhone(
                    request.name(),
                    request.phone()
                );

            User user;

            if (foundUsers.isEmpty()) {
                user = User.builder()
                    .name(request.name())
                    .phone(request.phone())
                    .age(request.age())
                    .residence(request.residence())
                    .allUserCount(request.count())
                    .birthYMD(request.birthYMD())
                    .gender(request.gender())
                    .privacyAgreed(request.privacyAgreed())
                    .build();

                user = userRepository.save(user);

            } else {
                user = foundUsers.get(0);
            }

            logRepository.save(log);
    }
}
