package com.example.burserker.gujeuk.domain.residence.service;

import com.example.burserker.gujeuk.domain.organ.facade.OrganFacade;
import com.example.burserker.gujeuk.domain.residence.domain.Residence;
import com.example.burserker.gujeuk.domain.residence.domain.repository.ResidenceRepository;
import com.example.burserker.gujeuk.domain.residence.exception.ResidenceAlreadyException;
import com.example.burserker.gujeuk.domain.residence.presentation.dto.request.ResidenceRequest;
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
