package com.example.gujeuck_server.domain.User.service;

import com.example.gujeuck_server.domain.User.dto.request.LoginRequest;
import com.example.gujeuck_server.domain.User.dto.response.TokenResponse;
import com.example.gujeuck_server.domain.User.entity.User;
import com.example.gujeuck_server.domain.User.repository.UserRepository;
import com.example.gujeuck_server.domain.User.exception.UserNotFoundException;
import com.example.gujeuck_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Transactional
    public TokenResponse login(LoginRequest request) {

        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(()-> UserNotFoundException.EXCEPTION);

        return jwtTokenProvider.receiveToken(request.getUserId());
    }
}
