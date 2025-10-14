package com.example.gujeuck_server.domain.admin.service;

import com.example.gujeuck_server.domain.admin.dto.UseListResponse;
import com.example.gujeuck_server.domain.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadAllUseListService {
    private final LogRepository logRepository;

    @Transactional(readOnly = true)
    public Slice<UseListResponse> readAllUseList(Pageable p) {
         Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.DESC, "visitDate")
                        .and(Sort.by(Sort.Direction.DESC, "id"))
                );

        return logRepository.findAllBy(pageable)
                .map(UseListResponse::from);
    }
}
