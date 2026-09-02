package com.example.gujeuck_server.domain.residence.service;

import com.example.gujeuck_server.domain.residence.domain.Residence;
import com.example.gujeuck_server.domain.residence.domain.repository.ResidenceRepository;
import com.example.gujeuck_server.domain.residence.exception.ResidenceNotFoundException;
import com.example.gujeuck_server.domain.residence.presentation.dto.request.ResidenceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateResidenceService {
    private final ResidenceRepository residenceRepository;

    @Transactional
    public void execute(Long id, ResidenceRequest residenceRequest) {
        Residence residence = residenceRepository.findById(id)
                .orElseThrow(() -> ResidenceNotFoundException.EXCEPTION);

        residence.updateResidence(residenceRequest.residenceName());
    }
}
