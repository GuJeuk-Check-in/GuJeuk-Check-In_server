package com.example.gujeuck_server.domain.residence.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.residence.domain.Residence;
import com.example.gujeuck_server.domain.residence.domain.repository.ResidenceRepository;
import com.example.gujeuck_server.domain.residence.exception.ResidenceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DeleteResidenceService {
    private final ResidenceRepository residenceRepository;
    private final OrganFacade organFacade;

    @Transactional
    public void execute(Long id) {
        Organ organ = organFacade.currentOrgan();

        Residence residence = residenceRepository.findById(id)
                .orElseThrow(() -> ResidenceNotFoundException.EXCEPTION);

        if (!residence.getOrgan().getId().equals(organ.getId())) {
            throw ResidenceNotFoundException.EXCEPTION;
        }

        int residenceIndex = residence.getResidenceIndex();

        residenceRepository.delete(residence);

        List<Residence> residences = residenceRepository.findAllByOrganIdAndResidenceIndexGreaterThan(organ.getId(), residenceIndex);

        for (Residence r : residences) {
            r.setResidenceIndex(r.getResidenceIndex() - 1);
        }
    }
}
