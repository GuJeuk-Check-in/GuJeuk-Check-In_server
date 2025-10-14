package com.example.gujeuck_server.domain.admin.service.etc;

import com.example.gujeuck_server.domain.admin.dto.request.AdminRequest;
import com.example.gujeuck_server.domain.admin.entity.Admin;
import com.example.gujeuck_server.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateAdminService {
    private final AdminRepository adminRepository;

    @Transactional
    public void createAdmin(AdminRequest request) {

        adminRepository.save(Admin.builder()
                        .password(request.getPassword())
                .build());
    }
}
