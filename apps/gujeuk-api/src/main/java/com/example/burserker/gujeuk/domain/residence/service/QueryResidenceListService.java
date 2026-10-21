package com.example.burserker.gujeuk.domain.residence.service;

import com.example.burserker.gujeuk.domain.residence.domain.repository.ResidenceRepository;
import com.example.burserker.gujeuk.domain.residence.presentation.dto.response.ResidenceResponse;
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
