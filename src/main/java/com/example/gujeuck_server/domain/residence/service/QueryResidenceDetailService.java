package com.example.gujeuck_server.domain.residence.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.purpose.exception.PurposeAccessDeniedException;
import com.example.gujeuck_server.domain.residence.domain.Residence;
import com.example.gujeuck_server.domain.residence.domain.repository.ResidenceRepository;
import com.example.gujeuck_server.domain.residence.exception.ResidenceAccessDeniedException;
import com.example.gujeuck_server.domain.residence.exception.ResidenceNotFoundException;
import com.example.gujeuck_server.domain.residence.presentation.dto.response.ResidenceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryResidenceDetailService {
    private final ResidenceRepository residenceRepository;
    private final OrganFacade organFacade;

    @Transactional(readOnly = true)
    public ResidenceResponse execute(Long id){
        Organ organ = organFacade.currentOrgan();

        Residence residence = residenceRepository.findById(id)
                .orElseThrow(() -> ResidenceNotFoundException.EXCEPTION);

        if (!residence.getOrgan().getId().equals(organ.getId())) {
            throw ResidenceAccessDeniedException.EXCEPTION;
        }

        return new ResidenceResponse(
                residence.getId(),
                residence.getResidenceName()
        );
    }
}
