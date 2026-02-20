package com.example.gujeuck_server.domain.organ.service;

import com.example.gujeuck_server.domain.organ.domain.repository.OrganRepository;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.OrganResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryOrganNameListService {

    private final OrganRepository organRepository;

    @Transactional(readOnly = true)
    public List<OrganResponse> execute() {

        List<OrganResponse> organResponses = organRepository.findAll()
                .stream()
                .map(OrganResponse::from)
                .toList();

        return organResponses;
    }
}
