package com.example.gujeuck_server.domain.service;

import com.example.gujeuck_server.domain.exception.NotFoundPurposeException;
import com.example.gujeuck_server.domain.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePurposeService {
    private final PurposeRepository purposeRepository;

    public void deletePurpose(Long id) {
        purposeRepository.findById(id).orElseThrow(
                () -> NotFoundPurposeException.EXCEPTION
        );
        purposeRepository.deleteById(id);
    }
}
