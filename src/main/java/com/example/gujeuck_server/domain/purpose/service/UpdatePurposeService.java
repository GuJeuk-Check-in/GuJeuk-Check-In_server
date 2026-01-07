package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.purpose.dto.request.PurposeRequest;
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.PurposeNotFoundException;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdatePurposeService {
    private final PurposeRepository purposeRepository;
    private final AdminFacade adminFacade;

    @Transactional
    public void updatePurpose(Long id, PurposeRequest purposeRequest) {
        adminFacade.currentUser();

        Purpose purpose = purposeRepository.findById(id)
                .orElseThrow(() -> PurposeNotFoundException.EXCEPTION);

        purpose.updatePurpose(purposeRequest.getPurpose());
    }

    @Transactional
    public void movePurpose(List<PurposeRequest> purposeRequests) {
        adminFacade.currentUser();

        for(int i = 0; i < purposeRequests.size(); i++) {
            Purpose purpose = purposeRepository.findById(purposeRequests.get(i).getId())
                    .orElseThrow(() -> PurposeNotFoundException.EXCEPTION);

            purpose.setIndexId(purposeRequests.get(i).getIndexId());
        }

        purposeRepository.saveAll(purposeRepository.findAll());
    }
}
