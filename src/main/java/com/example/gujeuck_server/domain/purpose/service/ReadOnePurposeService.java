package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import com.example.gujeuck_server.domain.purpose.presentation.dto.response.PurposeResponse;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadOnePurposeService {
    private final PurposeFacade purposeFacade;

    @Transactional(readOnly = true)
    public PurposeResponse readById(Long id) {

        Purpose purpose = purposeFacade.getPurposeById(id);

        return new PurposeResponse(purpose.getId(), purpose.getPurpose());
    }
}
