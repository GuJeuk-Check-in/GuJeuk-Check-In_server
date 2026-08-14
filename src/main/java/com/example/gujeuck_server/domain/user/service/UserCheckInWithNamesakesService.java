package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.exception.UserNotFoundException;
import com.example.gujeuck_server.domain.user.presentation.dto.request.UserCheckInRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCheckInWithNamesakesService {

    private final UserRepository userRepository;
    private final UserCheckInService userCheckInService;

    @Transactional
    public void execute(UserCheckInRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        // 프론트가 넘겨준 userId는 정상적으로 그대로 처리한다(실패 시 예외 그대로 전파).
        userCheckInService.execute(request);

        // 동명이인이 있으면 서버가 알아서 같이 체크인한다. 이름만으로 매칭하는 구조상
        // /user에서는 첫 번째 사람만 프론트에 알려주기 때문에, 나머지 동명이인은
        // 여기서 best-effort로 처리하고 실패해도 원래 요청은 영향받지 않게 한다.
        userRepository.findAllByName(user.getName()).stream()
                .filter(namesake -> !namesake.getId().equals(user.getId()))
                .forEach(namesake -> checkInNamesakeSafely(namesake, request));
    }

    private void checkInNamesakeSafely(User namesake, UserCheckInRequest original) {
        try {
            UserCheckInRequest namesakeRequest = UserCheckInRequest.create(
                    namesake.getId(),
                    original.maleCount(),
                    original.femaleCount(),
                    original.purpose(),
                    original.visitTime(),
                    null
            );

            userCheckInService.execute(namesakeRequest);
        } catch (Exception exception) {
            log.warn("동명이인 체크인 실패: namesakeUserId={}", namesake.getId(), exception);
        }
    }
}
