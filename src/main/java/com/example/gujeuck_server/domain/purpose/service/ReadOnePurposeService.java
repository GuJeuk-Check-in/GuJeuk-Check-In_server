package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.purpose.dto.response.PurposeResponse;
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.PurposeNotFoundException;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadOnePurposeService {
    private final PurposeRepository purposeRepository;

    public PurposeResponse readById(Long id) {
        Purpose purpose = purposeRepository.findById(id).orElseThrow(
                () -> PurposeNotFoundException.EXCEPTION);

        return new PurposeResponse(purpose.getId(), purpose.getPurpose());
    }
}
