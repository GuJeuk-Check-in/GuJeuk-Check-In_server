package com.example.gujeuck_server.domain.service;

import com.example.gujeuck_server.domain.dto.PurposeRequest;
import com.example.gujeuck_server.domain.entity.Purpose;
import com.example.gujeuck_server.domain.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePurposeService {
    private final PurposeRepository purposeRepository;

    public void createPurpose(PurposeRequest purposeDto) {
        Purpose purpose = Purpose.builder()
                .purpose(purposeDto.getPurpose())
                .purposeImage(purposeDto.getPurposeImage())
                .build();

        purposeRepository.save(purpose);
    }
}
