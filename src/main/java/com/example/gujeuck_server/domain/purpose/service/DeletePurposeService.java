package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.PurposeNotFoundException;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        purposeRepository.delete(purpose);
    }
}
