package com.example.gujeuck_server.domain.admin.service.token;

import com.example.gujeuck_server.domain.admin.dto.response.AccessTokenResponse;
import com.example.gujeuck_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReissueService {
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AccessTokenResponse reissue(String token) {
        String password = jwtTokenProvider.getUsernameFromToken(token);

        String newAccessToken = jwtTokenProvider.createAccessToken(password);
        Date accessExpiredAt = jwtTokenProvider.getExpirationDateFromToken(token);

        return AccessTokenResponse.builder()
                .accessToken(newAccessToken)
                .accessExpiredAt(accessExpiredAt)
                .build();
    }
}