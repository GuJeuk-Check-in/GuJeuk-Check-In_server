package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.request.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserService {
    private final UserRepository userRepository;

    public void execute(Long id, UpdateUserRequest request) {
        // 유저를 데이터베이스에서 id로 조회
        // 유저가 없다면 오류
        // request 값으로 해당 유저의 정보를 수정
            // 수정 요청된 userId가 다른 유저와 중복되면 오류
    }
}
