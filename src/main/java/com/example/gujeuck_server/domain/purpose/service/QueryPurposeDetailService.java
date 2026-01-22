package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.admin.domain.Admin;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.purpose.exception.PurposeAccessDeniedException;
import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.presentation.dto.response.PurposeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryPurposeDetailService {
    private final PurposeFacade purposeFacade;
    private final AdminFacade adminFacade;

    @Transactional(readOnly = true)
    public PurposeResponse execute(Long id) {
        Admin admin = adminFacade.currentUser();

        Purpose purpose = purposeFacade.getPurposeById(id);

        if (!purpose.getAdmin().getId().equals(admin.getId())) {
            throw PurposeAccessDeniedException.EXCEPTION;
        }

        return new PurposeResponse(purpose.getId(), purpose.getPurposeName());
    }
}
