package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.PurposeNotFoundException;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;
import com.example.gujeuck_server.infrastructure.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletePurposeService {
    private final PurposeRepository purposeRepository;
    private final S3Service s3Service;

    @Transactional
    public void deletePurpose(Long id) {
        Purpose purpose = purposeRepository.findById(id).orElseThrow(
                () -> PurposeNotFoundException.EXCEPTION
        );

        s3Service.delete(purpose.getPurposeImage());

        purposeRepository.delete(purpose);
    }
}
