package com.example.gujeuck_server.domain.admin.service;

import com.example.gujeuck_server.domain.admin.presentation.dto.request.AdminRequest;
import com.example.gujeuck_server.domain.admin.domain.Admin;
import com.example.gujeuck_server.domain.admin.domain.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAdminService {
    private final AdminRepository adminRepository;

    @Transactional
    public void execute(AdminRequest request) {

        adminRepository.save(Admin.builder()
                        .password(request.getPassword())
                .build());
    }
}
