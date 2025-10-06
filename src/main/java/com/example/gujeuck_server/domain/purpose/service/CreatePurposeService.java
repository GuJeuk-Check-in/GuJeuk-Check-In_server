package com.example.gujeuck_server.domain.Purpose.service;

import com.example.gujeuck_server.domain.Purpose.dto.PurposeRequest;
import com.example.gujeuck_server.domain.Purpose.entity.Purpose;
import com.example.gujeuck_server.domain.Purpose.repository.PurposeRepository;
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
