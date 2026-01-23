package com.example.gujeuck_server.domain.organ.facade;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.exception.OrganNotFoundException;
import com.example.gujeuck_server.domain.organ.domain.repository.OrganRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrganFacade {
    private final OrganRepository organRepository;

    public Organ currentOrgan() {
        String organName = SecurityContextHolder.getContext().getAuthentication().getName();

        return organRepository.findByOrganName(organName)
                .orElseThrow(() -> OrganNotFoundException.EXCEPTION);
    }

}
