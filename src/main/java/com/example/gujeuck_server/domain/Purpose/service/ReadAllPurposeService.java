package com.example.gujeuck_server.domain.service;

import com.example.gujeuck_server.domain.dto.PurposeResponse;
import com.example.gujeuck_server.domain.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllPurposeService {
    private final PurposeRepository purposeRepository;

    public List<PurposeResponse> readAll() {
        return purposeRepository.findAll().stream()
                .map(purpose -> new PurposeResponse(purpose.getId(), purpose.getPurpose(), purpose.getPurposeImage()))
                .toList();
    }
}
