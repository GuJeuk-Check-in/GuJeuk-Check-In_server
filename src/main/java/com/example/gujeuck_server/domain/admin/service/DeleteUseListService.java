package com.example.gujeuck_server.domain.admin.service;

import com.example.gujeuck_server.domain.admin.exception.LogNotFountException;
import com.example.gujeuck_server.domain.log.entity.Log;
import com.example.gujeuck_server.domain.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUseListService {
    private final LogRepository logRepository;

    public void deleteUseList(Long id) {
        Log log = logRepository.findById(id).orElseThrow(
                () -> LogNotFountException.EXCEPTION
        );
        logRepository.delete(log);
    }
}
