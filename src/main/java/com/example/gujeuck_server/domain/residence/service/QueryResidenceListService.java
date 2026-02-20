package com.example.gujeuck_server.domain.residence.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.residence.domain.repository.ResidenceRepository;
import com.example.gujeuck_server.domain.residence.presentation.dto.response.ResidenceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryResidenceListService {
    private final ResidenceRepository residenceRepository;
    private final OrganFacade organFacade;


    @Transactional(readOnly = true)
    public List<ResidenceResponse> execute(){
        Organ organ = organFacade.currentOrgan();

        return residenceRepository.findAllByOrganIdOrderByResidenceIndexAsc(organ.getId()).stream()
                .map(ResidenceResponse::from)
                .toList();
    }
}
