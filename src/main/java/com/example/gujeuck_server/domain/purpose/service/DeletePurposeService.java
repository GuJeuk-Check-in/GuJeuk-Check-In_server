package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.PurposeNotFoundException;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeletePurposeService {
    private final PurposeRepository purposeRepository;
    private final AdminFacade adminFacade;

    @Transactional
    public void deletePurpose(Long id) {
        adminFacade.currentUser();

        Purpose purpose = purposeRepository.findById(id).orElseThrow(
                () -> PurposeNotFoundException.EXCEPTION);

        int purposeIndex = purpose.getPurposeIndex();

        purposeRepository.delete(purpose);

        List<Purpose> purposes = purposeRepository.findAllByPurposeIndexGreaterThan(purposeIndex);

        for (Purpose p : purposes) {
            p.setPurposeIndex(p.getPurposeIndex() - 1);
        }
    }
}
