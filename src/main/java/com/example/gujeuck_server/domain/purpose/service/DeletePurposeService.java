package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.domain.repository.PurposeRepository;
import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletePurposeService {
    private final PurposeRepository purposeRepository;
    private final AdminFacade adminFacade;
    private final PurposeFacade purposeFacade;

    @Transactional
    public void deletePurpose(Long id) {
        adminFacade.currentUser();

        Purpose purpose = purposeFacade.getPurposeById(id);

        purposeRepository.delete(purpose);
    }
}
