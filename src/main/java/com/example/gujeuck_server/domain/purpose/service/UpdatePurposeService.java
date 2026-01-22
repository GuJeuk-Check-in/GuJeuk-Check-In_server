package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.admin.domain.Admin;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.purpose.exception.PurposeAccessDeniedException;
import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import com.example.gujeuck_server.domain.purpose.presentation.dto.request.PurposeRequest;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdatePurposeService {
    private final AdminFacade adminFacade;
    private final PurposeFacade purposeFacade;

    @Transactional
    public void updatePurpose(Long id, PurposeRequest purposeRequest) {
        Admin admin = adminFacade.currentUser();

        Purpose purpose = purposeFacade.getPurposeById(id);

        if (!purpose.getAdmin().getId().equals(admin.getId())) {
            throw PurposeAccessDeniedException.EXCEPTION;
        }

        purpose.updatePurpose(purposeRequest.getPurpose());
    }
}
