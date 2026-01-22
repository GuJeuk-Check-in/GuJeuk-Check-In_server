package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
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
    private final OrganFacade organFacade;
    private final PurposeFacade purposeFacade;

    @Transactional
    public void execute(Long id, PurposeRequest purposeRequest) {
        Organ organ = organFacade.currentUser();

        Purpose purpose = purposeFacade.getPurposeById(id);

        if (!purpose.getOrgan().getId().equals(organ.getId())) {
            throw PurposeAccessDeniedException.EXCEPTION;
        }

        purpose.updatePurpose(purposeRequest.getPurpose());
    }
}
