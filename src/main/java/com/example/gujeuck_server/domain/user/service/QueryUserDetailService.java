package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.exception.UserNotFoundException;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryUserDetailService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDetailResponse execute(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        return UserDetailResponse.from(user);
    }
}
