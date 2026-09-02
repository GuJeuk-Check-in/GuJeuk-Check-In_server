package com.example.gujeuck_server.domain.residence.service;

import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.residence.domain.Residence;
import com.example.gujeuck_server.domain.residence.domain.repository.ResidenceRepository;
import com.example.gujeuck_server.domain.residence.exception.ResidenceAlreadyException;
import com.example.gujeuck_server.domain.residence.presentation.dto.request.ResidenceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreateResidenceService {
    private final ResidenceRepository residenceRepository;
    private final OrganFacade organFacade;

    @Transactional
    public void execute(Long organId, ResidenceRequest residenceRequest) {
        if(residenceRepository.findByOrganIdAndResidenceName(organId, residenceRequest.residenceName()).isPresent()) {
            throw ResidenceAlreadyException.EXCEPTION;
        }

        int residenceIndex = residenceRepository.findMaxResidenceIndexByOrganId(organId) + 1;

        residenceRepository.save(
                Residence.builder()
                        .residenceName(residenceRequest.residenceName())
                        .residenceIndex(residenceIndex)
                        .organ(organFacade.getOrganReference(organId))
                        .build()
        );
    }
}
