package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.domain.repository.PurposeRepository;
import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeletePurposeService {
    private final PurposeRepository purposeRepository;
    private final PurposeFacade purposeFacade;

    @Transactional
    public void execute(Long organId, Long id) {
        Purpose purpose = purposeFacade.getPurposeById(id);

        int purposeIndex = purpose.getPurposeIndex();

        purposeRepository.delete(purpose);

        List<Purpose> purposes = purposeRepository.findAllByOrganIdAndPurposeIndexGreaterThan(organId, purposeIndex);

        for (Purpose p : purposes) {
            p.updatePurposeIndex(p.getPurposeIndex() - 1);
        }
    }
}
