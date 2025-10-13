package com.example.gujeuck_server.domain.admin.service.facade;

import com.example.gujeuck_server.domain.admin.entity.Admin;
import com.example.gujeuck_server.domain.admin.exception.AdminNotFoundException;
import com.example.gujeuck_server.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminFacade {
    private final AdminRepository adminRepository;

    public Admin currentUser() {
        String password = SecurityContextHolder.getContext().getAuthentication().getName();

        return adminRepository.findByPassword(password)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);
    }

}
