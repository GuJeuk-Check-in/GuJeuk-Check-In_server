package com.example.gujeuck_server.domain.admin.service;

import com.example.gujeuck_server.domain.admin.dto.UseListResponse;
import com.example.gujeuck_server.domain.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllUseListService {
    private final LogRepository logRepository;

    @Transactional(readOnly = true)
    public List<UseListResponse> readAllUseList() {
        return logRepository.findAll().stream()
                .map(log -> new UseListResponse(log.getId(), log.getName(), log.getAge(), log.getPhone(), log.getMaleCount(), log.getMaleCount(), log.getPurpose().getId(), log.getVisitDate(), log.isPrivacyAgreed()))
                .toList();
    }
}
