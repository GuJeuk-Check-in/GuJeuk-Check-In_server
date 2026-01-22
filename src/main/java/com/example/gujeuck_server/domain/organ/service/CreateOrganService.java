package com.example.gujeuck_server.domain.organ.service;

import com.example.gujeuck_server.domain.organ.presentation.dto.request.OrganRequest;
import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.domain.repository.OrganRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateOrganService {
    private final OrganRepository organRepository;

    @Transactional
    public void execute(OrganRequest request) {

        organRepository.save(Organ.builder()
                        .password(request.getPassword())
                .build());
    }
}
