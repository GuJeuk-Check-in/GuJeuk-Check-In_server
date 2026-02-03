package com.example.gujeuck_server.domain.residence.service;

import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.residence.domain.repository.ResidenceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateResidenceService {
    private final ResidenceRepository residenceRepository;
    private final OrganFacade organFacade;

    
}
