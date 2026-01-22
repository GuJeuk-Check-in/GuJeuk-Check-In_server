package com.example.gujeuck_server.domain.admin.service;

import com.example.gujeuck_server.domain.admin.presentation.dto.request.AdminRequest;
import com.example.gujeuck_server.domain.admin.presentation.dto.response.TokenResponse;
import com.example.gujeuck_server.domain.admin.domain.Admin;
import com.example.gujeuck_server.domain.admin.exception.AdminNotFoundException;
import com.example.gujeuck_server.domain.admin.domain.repository.AdminRepository;
import com.example.gujeuck_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginAdminService {
    private final AdminRepository adminRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public TokenResponse execute(AdminRequest request) {
        Admin admin = adminRepository.findByPassword(request.getPassword())
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        return jwtTokenProvider.receiveToken(admin.getPassword());
    }
}