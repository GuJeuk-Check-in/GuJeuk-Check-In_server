package com.example.gujeuck_server.domain.Purpose.service;

import com.example.gujeuck_server.domain.Purpose.dto.PurposeResponse;
import com.example.gujeuck_server.domain.Purpose.repository.PurposeRepository;
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
