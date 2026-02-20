package com.example.gujeuck_server.domain.organ.service;

import com.example.gujeuck_server.domain.organ.domain.enums.Client;
import com.example.gujeuck_server.domain.organ.presentation.dto.request.LoginOrganRequest;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.TokenResponse;
import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.exception.OrganNotFoundException;
import com.example.gujeuck_server.domain.organ.domain.repository.OrganRepository;
import com.example.gujeuck_server.domain.user.exception.PasswordMismatchException;
import com.example.gujeuck_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginOrganService {
    private final OrganRepository organRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenResponse execute(LoginOrganRequest request) {

        Organ organ = organRepository.findByOrganName(request.getOrganName())
                .orElseThrow(() -> OrganNotFoundException.EXCEPTION);

        if (!passwordEncoder.matches(request.getPassword(), organ.getPassword())) {
            throw PasswordMismatchException.EXCEPTION;
        }

        Client client = Client.valueOf(request.getClient().toUpperCase());

        return jwtTokenProvider.receiveToken(organ.getOrganName(), client);
    }
}
