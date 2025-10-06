package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.purpose.dto.response.PurposeResponse;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;
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
