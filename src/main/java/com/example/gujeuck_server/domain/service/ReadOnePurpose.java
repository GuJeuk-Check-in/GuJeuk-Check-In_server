package com.example.gujeuck_server.domain.service;

import com.example.gujeuck_server.domain.entity.Purpose;
import com.example.gujeuck_server.domain.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadOnePurpose {
    private final PurposeRepository purposeRepository;

    public Purpose readById(Long id) {
        Purpose purpose = purposeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 방문목적입니다."));

        return purpose;
    }
}
