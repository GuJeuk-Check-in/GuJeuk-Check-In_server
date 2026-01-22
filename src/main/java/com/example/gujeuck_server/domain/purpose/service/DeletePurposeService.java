package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.admin.domain.Admin;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.domain.repository.PurposeRepository;
import com.example.gujeuck_server.domain.purpose.exception.PurposeAccessDeniedException;
import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeletePurposeService {
    private final PurposeRepository purposeRepository;
    private final AdminFacade adminFacade;
    private final PurposeFacade purposeFacade;

    @Transactional
    public void deletePurpose(Long id) {
        Admin admin = adminFacade.currentUser();

        Purpose purpose = purposeFacade.getPurposeById(id);

        if (!purpose.getAdmin().getId().equals(admin.getId())) {
            throw PurposeAccessDeniedException.EXCEPTION;
        }

        int purposeIndex = purpose.getPurposeIndex();

        purposeRepository.delete(purpose);

        List<Purpose> purposes = purposeRepository.findAllByPurposeIndexGreaterThan(purposeIndex);

        for (Purpose p : purposes) {
            p.setPurposeIndex(p.getPurposeIndex() - 1);
        }
    }
}
