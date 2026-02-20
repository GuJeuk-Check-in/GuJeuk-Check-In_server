package com.example.gujeuck_server.domain.organ.service;

import com.example.gujeuck_server.domain.organ.domain.RefreshToken;
import com.example.gujeuck_server.domain.organ.domain.enums.Client;
import com.example.gujeuck_server.domain.organ.domain.repository.RefreshTokenRepository;
import com.example.gujeuck_server.domain.organ.exception.OrganNotFoundException;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.TokenResponse;
import com.example.gujeuck_server.domain.user.exception.RefreshTokenNotFoundException;
import com.example.gujeuck_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReissueService {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public TokenResponse execute(Authentication authentication) {
        String organName = authentication.getName();

        RefreshToken refreshToken = refreshTokenRepository.findByOrganName(organName)
                .orElseThrow(() -> RefreshTokenNotFoundException.EXCEPTION);

        Client client = Client.valueOf(refreshToken.getClient());

        return jwtTokenProvider.receiveToken(organName, client);
    }
}