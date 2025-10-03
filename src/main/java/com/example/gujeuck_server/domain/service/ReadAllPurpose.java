package com.example.gujeuck_server.domain.service;

import com.example.gujeuck_server.domain.entity.Purpose;
import com.example.gujeuck_server.domain.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllPurpose {
    private final PurposeRepository purposeRepository;

    public List<Purpose> readAll() {
        List<Purpose> purposes = purposeRepository.findAll();

        return purposes;
    }
}
