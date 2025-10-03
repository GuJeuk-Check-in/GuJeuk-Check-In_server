package com.example.gujeuck_server.domain.service;

import com.example.gujeuck_server.domain.entity.Purpose;
import com.example.gujeuck_server.domain.exception.NotFoundPurposeException;
import com.example.gujeuck_server.domain.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadOnePurpose {
    private final PurposeRepository purposeRepository;

    public Purpose readById(Long id) {
        return purposeRepository.findById(id).orElseThrow(
                () -> NotFoundPurposeException.EXCEPTION);
    }
}
