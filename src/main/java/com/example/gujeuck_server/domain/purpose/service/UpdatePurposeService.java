package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.purpose.dto.request.PurposeRequest;
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.PurposeNotFoundException;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdatePurposeService {
    private final PurposeRepository purposeRepository;

    @Transactional
    public void updatePurpose(Long id, PurposeRequest purposeRequest) {
        Purpose purpose = purposeRepository.findById(id)
                .orElseThrow(() -> PurposeNotFoundException.EXCEPTION);

        purpose.updatePurpose(purposeRequest.getPurpose(),  purposeRequest.getPurposeImage());
    }
}
