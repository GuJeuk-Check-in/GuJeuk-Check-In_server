package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.purpose.exception.PurposeAlreadyExistException;
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
    private final OrganFacade organFacade;

    @Transactional
    public void execute(PurposeRequest request) {
        Organ organ = organFacade.currentUser();

        if(purposeRepository.findByPurposeName(request.getPurpose()).isPresent()) {
            throw PurposeAlreadyExistException.EXCEPTION;
        }

        int purposeIndex = purposeRepository.findMaxPurposeIndexByOrganId(organ.getId()) + 1;

        purposeRepository.save(
                Purpose.builder()
                        .purposeName(request.getPurpose())
                        .purposeIndex(purposeIndex)
                        .organ(organ)
                        .build()
        );
    }
}
