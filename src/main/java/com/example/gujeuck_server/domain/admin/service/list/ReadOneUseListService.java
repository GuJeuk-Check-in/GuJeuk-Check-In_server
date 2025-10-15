package com.example.gujeuck_server.domain.admin.service.list;

import com.example.gujeuck_server.domain.admin.dto.response.UseListResponse;
import com.example.gujeuck_server.domain.admin.exception.LogNotFountException;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.log.entity.Log;
import com.example.gujeuck_server.domain.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadOneUseListService {
    private final LogRepository logRepository;
    private final AdminFacade adminFacade;

    public UseListResponse readOneUseList(Long id) {
        adminFacade.currentUser();

        Log log = logRepository.findById(id).orElseThrow(
                () -> LogNotFountException.EXCEPTION
        );

        return new UseListResponse(log);
    }
}