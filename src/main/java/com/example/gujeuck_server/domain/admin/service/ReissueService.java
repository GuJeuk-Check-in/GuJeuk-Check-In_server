package com.example.gujeuck_server.domain.admin.service;

import com.example.gujeuck_server.domain.admin.presentation.dto.response.TokenResponse;
import com.example.gujeuck_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReissueService {
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenResponse execute(Authentication authentication) {
        return jwtTokenProvider.receiveToken(authentication.getName());
    }
}