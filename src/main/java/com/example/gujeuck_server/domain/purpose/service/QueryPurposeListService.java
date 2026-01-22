package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.purpose.domain.repository.PurposeRepository;
import com.example.gujeuck_server.domain.purpose.presentation.dto.response.PurposeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryPurposeListService {
    private final PurposeRepository purposeRepository;
    private final OrganFacade organFacade;

    @Transactional(readOnly = true)
    public List<PurposeResponse> execute() {

        Organ organ = organFacade.currentUser();

        return purposeRepository.findAllByOrganIdOrderByPurposeIndexAsc(organ.getId()).stream()
                .map(PurposeResponse::from)
                .toList();
    }
}
