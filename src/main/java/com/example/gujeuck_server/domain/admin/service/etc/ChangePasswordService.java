package com.example.gujeuck_server.domain.admin.service.etc;

import com.example.gujeuck_server.domain.admin.presentation.dto.request.ChangePasswordRequest;
import com.example.gujeuck_server.domain.admin.domain.Admin;
import com.example.gujeuck_server.domain.admin.exception.AdminNotFoundException;
import com.example.gujeuck_server.domain.admin.exception.InvalidPasswordConfirmException;
import com.example.gujeuck_server.domain.admin.exception.SameOldPasswordException;
import com.example.gujeuck_server.domain.admin.domain.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {
    private final AdminRepository adminRepository;

    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        Admin admin = adminRepository.findByPassword(request.getOldPassword())
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw InvalidPasswordConfirmException.EXCEPTION;
        }

        if (admin.getPassword().equals(request.getNewPassword())) {
            throw SameOldPasswordException.EXCEPTION;
        }

        admin.changePassword(request.getNewPassword());
    }
}
