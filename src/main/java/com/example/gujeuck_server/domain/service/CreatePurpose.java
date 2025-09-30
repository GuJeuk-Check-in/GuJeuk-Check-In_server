package com.example.gujeuck_server.domain.service;

import com.example.gujeuck_server.domain.entity.Purpose;
import com.example.gujeuck_server.domain.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePurpose {
    private PurposeRepository purposeRepository;

    public void createPurpose(Purpose purpose) {
        Purpose newPurpose = new Purpose();

        newPurpose.setPurpose(purpose.getPurpose());
        newPurpose.setPurposeImage(purpose.getPurposeImage());

        purposeRepository.save(newPurpose);
    }
}
