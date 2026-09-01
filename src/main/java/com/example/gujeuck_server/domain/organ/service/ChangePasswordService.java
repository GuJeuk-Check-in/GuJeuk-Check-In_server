package com.example.gujeuck_server.domain.organ.service;

import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.organ.presentation.dto.request.ChangePasswordRequest;
import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.exception.InvalidPasswordConfirmException;
import com.example.gujeuck_server.domain.organ.exception.SameOldPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {
    private final OrganFacade organFacade;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(ChangePasswordRequest request) {
        Organ organ = organFacade.currentOrgan();

        if (passwordEncoder.matches(request.newPassword(), organ.getPassword())) {
            throw SameOldPasswordException.EXCEPTION;
        }

        if (!request.newPassword().equals(request.confirmNewPassword())) {
            throw InvalidPasswordConfirmException.EXCEPTION;
        }

        organ.changePassword(passwordEncoder.encode(request.newPassword()));
    }
}
