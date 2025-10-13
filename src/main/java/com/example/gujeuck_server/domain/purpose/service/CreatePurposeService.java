package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.purpose.dto.request.PurposeRequest;
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;
import com.example.gujeuck_server.infrastructure.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CreatePurposeService {
    private final PurposeRepository purposeRepository;
    private final S3Service s3Service;

    @Transactional
    public void createPurpose(PurposeRequest purposeDto) {

        Purpose purpose = Purpose.builder()
                .purpose(purposeDto.getPurpose())
                .build();

        purposeRepository.save(purpose);
    }
}
