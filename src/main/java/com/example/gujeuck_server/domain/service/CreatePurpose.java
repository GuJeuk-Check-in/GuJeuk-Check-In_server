package com.example.gujeuck_server.domain.service;

import com.example.gujeuck_server.domain.dto.PurposeRequest;
import com.example.gujeuck_server.domain.entity.Purpose;
import com.example.gujeuck_server.domain.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePurpose {
    private final PurposeRepository purposeRepository;

    public void createPurpose(PurposeRequest purposeRequest) {
        Purpose purpose = new Purpose();

        purpose.setPurpose(purposeRequest.getPurpose());
        purpose.setPurposeImage(purposeRequest.getPurposeImage());

        purposeRepository.save(purpose);
    }
}
