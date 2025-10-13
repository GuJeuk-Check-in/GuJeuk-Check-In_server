package com.example.gujeuck_server.domain.admin.service;

import com.example.gujeuck_server.domain.admin.dto.request.AdminRequest;
import com.example.gujeuck_server.domain.admin.entity.Admin;
import com.example.gujeuck_server.domain.admin.exception.AdminNotFoundException;
import com.example.gujeuck_server.domain.admin.repository.AdminRepository;
import com.example.gujeuck_server.domain.user.dto.response.TokenResponse;
import com.example.gujeuck_server.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminLoginService {
    private final AdminRepository adminRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenResponse login(AdminRequest request) {
        Admin admin = adminRepository.findByPassword(request.getPassword())
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        return jwtTokenProvider.receiveToken(request.getPassword());
    }
}
