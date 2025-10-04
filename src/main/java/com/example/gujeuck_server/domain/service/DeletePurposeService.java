package com.example.gujeuck_server.domain.service;

import com.example.gujeuck_server.domain.entity.Purpose;
import com.example.gujeuck_server.domain.exception.NotFoundPurposeException;
import com.example.gujeuck_server.domain.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletePurposeService {
    private final PurposeRepository purposeRepository;

    @Transactional
    public void deletePurpose(Long id) {
        Purpose purpose = purposeRepository.findById(id).orElseThrow(
                () -> NotFoundPurposeException.EXCEPTION
        );

        purposeRepository.delete(purpose);
    }
}
