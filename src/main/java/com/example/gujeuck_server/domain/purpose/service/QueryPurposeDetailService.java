package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
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
    private final OrganFacade organFacade;

    @Transactional(readOnly = true)
    public PurposeResponse execute(Long id) {
        Organ organ = organFacade.currentOrgan();

        Purpose purpose = purposeFacade.getPurposeById(id);

        if (!purpose.getOrgan().getId().equals(organ.getId())) {
            throw PurposeAccessDeniedException.EXCEPTION;
        }

        return new PurposeResponse(purpose.getId(), purpose.getPurposeName());
    }
}
