package com.example.gujeuck_server.domain.Purpose.service;

import com.example.gujeuck_server.domain.Purpose.dto.PurposeResponse;
import com.example.gujeuck_server.domain.Purpose.entity.Purpose;
import com.example.gujeuck_server.domain.Purpose.exception.NotFoundPurposeException;
import com.example.gujeuck_server.domain.Purpose.repository.PurposeRepository;
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
