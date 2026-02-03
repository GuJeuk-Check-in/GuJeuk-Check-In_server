package com.example.gujeuck_server.domain.residence.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.residence.domain.Residence;
import com.example.gujeuck_server.domain.residence.domain.repository.ResidenceRepository;
import com.example.gujeuck_server.domain.residence.exception.ResidenceAlreadyException;
import com.example.gujeuck_server.domain.residence.presentation.dto.request.ResidenceRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateResidenceService {
    private final ResidenceRepository residenceRepository;
    private final OrganFacade organFacade;

    public void execute(ResidenceRequest residenceRequest) {
        Organ organ = organFacade.currentOrgan();

        if(residenceRepository.findByOrganIdAndResidenceName(organ.getId(), residenceRequest.getResidenceName()).isPresent()) {
            throw ResidenceAlreadyException.EXCEPTION;
        }

        int residenceIndex = residenceRepository.findMaxResidenceIndexByOrganId(organ.getId()) + 1;

        residenceRepository.save(
                Residence.builder()
                        .residenceName(residenceRequest.getResidenceName())
                        .residenceIndex(residenceIndex)
                        .organ(organ)
                        .build()
        );
    }
}
