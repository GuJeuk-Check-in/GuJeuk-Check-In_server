package com.example.gujeuck_server.domain.admin.service.token;

import com.example.gujeuck_server.domain.admin.entity.RefreshToken;
import com.example.gujeuck_server.domain.user.exception.InvalidTokenException;
import com.example.gujeuck_server.domain.user.repository.RefreshTokenRepository;
import com.example.gujeuck_server.domain.user.exception.RefreshTokenNotFoundException;
import com.example.gujeuck_server.domain.admin.dto.request.RefreshTokenRequest;
import com.example.gujeuck_server.domain.admin.dto.response.TokenResponse;
import com.example.gujeuck_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReissueService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenResponse reissue(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getToken())
                .orElseThrow(() -> RefreshTokenNotFoundException.EXCEPTION);
        return jwtTokenProvider.receiveToken(refreshToken.getPassword());
    }
}
