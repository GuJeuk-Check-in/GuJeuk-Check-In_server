package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.admin.presentation.dto.response.LogResponse;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryLogListService {
    private final LogRepository logRepository;
    private final AdminFacade adminFacade;

    @Transactional(readOnly = true)
    public Slice<LogResponse> readAllUseList(Pageable p) {
        adminFacade.currentUser();

         Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.DESC, "visitDate")
                        .and(Sort.by(Sort.Direction.DESC, "id"))
                );

        return logRepository.findAllBy(pageable)
                .map(LogResponse::from);
    }
}
