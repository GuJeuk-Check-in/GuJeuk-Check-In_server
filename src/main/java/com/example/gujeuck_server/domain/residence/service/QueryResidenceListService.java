package com.example.gujeuck_server.domain.residence.service;

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

    @Transactional(readOnly = true)
    public List<ResidenceResponse> execute(){
        return residenceRepository.findAllByOrderByResidenceIndexAsc().stream()
                .map(ResidenceResponse::from)
                .toList();
    }
}
