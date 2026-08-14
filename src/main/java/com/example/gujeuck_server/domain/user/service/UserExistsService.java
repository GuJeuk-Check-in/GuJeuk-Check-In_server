package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.request.UserExistsRequest;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserExistsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserExistsService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserExistsResponse execute(UserExistsRequest request) {
        // 동명이인이 있어도 프론트는 배열 길이가 1이 아니면 실패로 처리하는 구조라,
        // 여기서는 첫 번째 매칭만 반환한다. 동명이인 전원 체크인은
        // UserCheckInWithNamesakesService가 서버 내부에서 처리한다.
        List<Long> userIds = userRepository.findAllByName(request.getName()).stream()
                .findFirst()
                .map(User::getId)
                .map(List::of)
                .orElse(List.of());

        return UserExistsResponse.of(!userIds.isEmpty(), userIds);
    }
}
