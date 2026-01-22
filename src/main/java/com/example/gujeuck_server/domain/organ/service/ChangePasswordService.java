package com.example.gujeuck_server.domain.organ.service;

import com.example.gujeuck_server.domain.organ.presentation.dto.request.ChangePasswordRequest;
import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.exception.OrganNotFoundException;
import com.example.gujeuck_server.domain.organ.exception.InvalidPasswordConfirmException;
import com.example.gujeuck_server.domain.organ.exception.SameOldPasswordException;
import com.example.gujeuck_server.domain.organ.domain.repository.OrganRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangePasswordService {
    private final OrganRepository organRepository;

    @Transactional
    public void execute(ChangePasswordRequest request) {
        Organ organ = organRepository.findByPassword(request.getOldPassword())
                .orElseThrow(() -> OrganNotFoundException.EXCEPTION);

        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw InvalidPasswordConfirmException.EXCEPTION;
        }

        if (organ.getPassword().equals(request.getNewPassword())) {
            throw SameOldPasswordException.EXCEPTION;
        }

        organ.changePassword(request.getNewPassword());
    }
}
