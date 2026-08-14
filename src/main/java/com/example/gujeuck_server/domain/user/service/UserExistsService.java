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
        List<Long> userIds = userRepository.findAllByName(request.getName()).stream()
                .map(User::getId)
                .toList();

        return UserExistsResponse.of(!userIds.isEmpty(), userIds);
    }
}
