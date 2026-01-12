package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.purpose.exception.PurposeAlreadyExistException;
import com.example.gujeuck_server.domain.purpose.presentation.dto.request.PurposeRequest;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.domain.repository.PurposeRepository;
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
    public void createPurpose(PurposeRequest request) {

        adminFacade.currentUser();

        if (purposeRepository.findByPurpose(request.getPurpose()).isPresent()) {
            throw PurposeAlreadyExistException.EXCEPTION;
        }

        List<Purpose> purposes = purposeRepository.findAll();

        purposeRepository.save(Purpose.builder()
                .purpose(request.getPurpose())
                .purposeIndex(purposes.size() + 1)
                .build());
    }
}
