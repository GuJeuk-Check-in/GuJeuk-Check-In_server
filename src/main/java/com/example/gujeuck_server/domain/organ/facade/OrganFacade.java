package com.example.gujeuck_server.domain.organ.facade;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.exception.OrganNotFoundException;
import com.example.gujeuck_server.domain.organ.domain.repository.OrganRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganFacade {
    private final OrganRepository organRepository;

    public Organ getOrganById(Long organId) {
        return organRepository.findById(organId)
                .orElseThrow(() -> OrganNotFoundException.EXCEPTION);
    }

    public Organ getOrganReference(Long organId) {
        return organRepository.getReferenceById(organId);
    }
}
