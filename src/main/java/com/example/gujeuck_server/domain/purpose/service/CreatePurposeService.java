package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.purpose.dto.request.PurposeRequest;
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreatePurposeService {
    private final PurposeRepository purposeRepository;
    private final AdminFacade adminFacade;

    @Transactional
    public void createPurpose(PurposeRequest purpose) {

        adminFacade.currentUser();

        List<Purpose> purposes = purposeRepository.findAll();

        purposeRepository.save(Purpose.builder()
                .id(purpose.getId())
                .purpose(purpose.getPurpose())
                .index(purposes.size() + 1)
                .build());
    }
}
