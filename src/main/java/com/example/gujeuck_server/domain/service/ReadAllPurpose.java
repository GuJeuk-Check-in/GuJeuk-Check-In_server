package com.example.gujeuck_server.domain.service;

import com.example.gujeuck_server.domain.dto.PurposeResponse;
import com.example.gujeuck_server.domain.entity.Purpose;
import com.example.gujeuck_server.domain.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllPurpose {
    private final PurposeRepository purposeRepository;

    public List<PurposeResponse> readAll() {
        List<PurposeResponse> purposes = purposeRepository.findAllPurpose();

        return purposes;
    }
}
