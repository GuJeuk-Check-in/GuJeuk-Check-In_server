package com.example.gujeuck_server.domain.organ.service;

import com.example.gujeuck_server.domain.organ.presentation.dto.request.OrganRequest;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.TokenResponse;
import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.exception.OrganNotFoundException;
import com.example.gujeuck_server.domain.organ.domain.repository.OrganRepository;
import com.example.gujeuck_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginOrganService {
    private final OrganRepository organRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenResponse execute(OrganRequest request) {
        Organ organ = organRepository.findByPassword(request.getPassword())
                .orElseThrow(() -> OrganNotFoundException.EXCEPTION);

        return jwtTokenProvider.receiveToken(organ.getPassword());
    }
}
