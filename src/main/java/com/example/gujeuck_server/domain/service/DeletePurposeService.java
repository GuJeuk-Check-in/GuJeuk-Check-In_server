package com.example.gujeuck_server.domain.service;

import com.example.gujeuck_server.domain.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePurposeService {
    private final PurposeRepository purposeRepository;

    public void deletePurpose(Long id) {
        purposeRepository.deleteById(id);
    }
}
