package com.example.gujeuck_server.domain.admin.service.token;

import com.example.gujeuck_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReissueService {
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public String reissue(String token) {

        String password = jwtTokenProvider.getUsernameFromToken(token);

        return jwtTokenProvider.receiveToken(password);
    }
}