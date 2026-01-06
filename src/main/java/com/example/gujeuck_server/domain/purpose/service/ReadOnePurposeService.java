package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.purpose.presentation.dto.response.PurposeResponse;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.PurposeNotFoundException;
import com.example.gujeuck_server.domain.purpose.domain.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadOnePurposeService {
    private final PurposeRepository purposeRepository;

    @Transactional(readOnly = true)
    public PurposeResponse readById(Long id) {

        Purpose purpose = purposeRepository.findById(id).orElseThrow(
                () -> PurposeNotFoundException.EXCEPTION);

        return new PurposeResponse(purpose.getId(), purpose.getPurpose());
    }
}
