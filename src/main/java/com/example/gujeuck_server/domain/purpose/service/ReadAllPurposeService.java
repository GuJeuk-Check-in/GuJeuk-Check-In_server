package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.purpose.domain.repository.PurposeRepository;
import com.example.gujeuck_server.domain.purpose.presentation.dto.response.PurposeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllPurposeService {
    private final PurposeRepository purposeRepository;

    @Transactional(readOnly = true)
    public List<PurposeResponse> readAll() {

        return purposeRepository.findAll().stream()
                .map(PurposeResponse::from)
                .toList();
    }
}
