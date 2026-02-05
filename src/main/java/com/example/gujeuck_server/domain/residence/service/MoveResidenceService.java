package com.example.gujeuck_server.domain.residence.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.residence.domain.Residence;
import com.example.gujeuck_server.domain.residence.domain.repository.ResidenceRepository;
import com.example.gujeuck_server.domain.residence.exception.ResidenceNotFoundException;
import com.example.gujeuck_server.domain.residence.presentation.dto.request.ResidenceMoveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MoveResidenceService {
    private final ResidenceRepository residenceRepository;
    private final OrganFacade organFacade;

    @Transactional
    public void execute(ResidenceMoveRequest residenceMoveRequest) {
        Organ organ = organFacade.currentOrgan();

        List<Long> residencesId = residenceMoveRequest.getResidenceId();
        List<Residence> residences = residenceRepository.findAllById(residenceMoveRequest.getResidenceId());

        if(residencesId.size() != residences.size()) {
            throw ResidenceNotFoundException.EXCEPTION;
        }

        Map<Long, Residence> residenceMap = residences.stream()
                .collect(Collectors.toMap(Residence::getId, residence -> residence));

        for(int i = 0; i < residenceMoveRequest.getResidenceId().size(); i++) {
            Residence residence = residenceMap.get(residenceMoveRequest.getResidenceId().get(i));

            if(residence == null) {
                throw ResidenceNotFoundException.EXCEPTION;
            }

            if(!residence.getOrgan().getId().equals(organ.getId())) {
                throw ResidenceNotFoundException.EXCEPTION;
            }

            residence.setResidenceIndex(i + 1);
        }
    }
}
