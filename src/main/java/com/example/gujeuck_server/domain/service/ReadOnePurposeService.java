package com.example.gujeuck_server.domain.service;

import com.example.gujeuck_server.domain.dto.PurposeResponse;
import com.example.gujeuck_server.domain.entity.Purpose;
import com.example.gujeuck_server.domain.exception.NotFoundPurposeException;
import com.example.gujeuck_server.domain.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadOnePurposeService {
    private final PurposeRepository purposeRepository;

    public PurposeResponse readById(Long id) {
        Purpose purpose = purposeRepository.findById(id).orElseThrow(
                () -> NotFoundPurposeException.EXCEPTION);

        return new PurposeResponse(purpose.getId(), purpose.getPurpose(), purpose.getPurposeImage());

    }
}
