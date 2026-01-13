package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.purpose.exception.PurposeNotFoundException;
import com.example.gujeuck_server.domain.purpose.presentation.dto.request.PurposeRequest;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.domain.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePurposeService {
    private final PurposeRepository purposeRepository;
    private final AdminFacade adminFacade;

    @Transactional
    public void createPurpose(PurposeRequest request) {
        adminFacade.currentUser();

        if(purposeRepository.findByPurpose(request.getPurpose()).isPresent()) {
            throw PurposeNotFoundException.EXCEPTION;
        }
        
        int purposeIndex = purposeRepository.findMaxPurposeIndex() + 1;

        purposeRepository.save(
                Purpose.builder()
                        .purpose(request.getPurpose())
                        .purposeIndex(purposeIndex)
                        .build()
        );
    }
}
