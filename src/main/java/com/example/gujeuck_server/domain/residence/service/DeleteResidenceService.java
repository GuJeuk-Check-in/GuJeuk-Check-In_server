package com.example.gujeuck_server.domain.residence.service;

import com.example.gujeuck_server.domain.residence.domain.Residence;
import com.example.gujeuck_server.domain.residence.domain.repository.ResidenceRepository;
import com.example.gujeuck_server.domain.residence.exception.ResidenceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DeleteResidenceService {
    private final ResidenceRepository residenceRepository;

    @Transactional
    public void execute(Long organId, Long id) {
        Residence residence = residenceRepository.findById(id)
                .orElseThrow(() -> ResidenceNotFoundException.EXCEPTION);

        int residenceIndex = residence.getResidenceIndex();

        residenceRepository.delete(residence);

        List<Residence> residences = residenceRepository.findAllByOrganIdAndResidenceIndexGreaterThan(organId, residenceIndex);

        for (Residence r : residences) {
            r.setResidenceIndex(r.getResidenceIndex() - 1);
        }
    }
}
