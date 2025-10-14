package com.example.gujeuck_server.domain.admin.service.list;

import com.example.gujeuck_server.domain.admin.exception.LogNotFountException;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.log.entity.Log;
import com.example.gujeuck_server.domain.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteUseListService {
    private final LogRepository logRepository;
    private final AdminFacade adminFacade;

    @Transactional
    public void deleteUseList(Long id) {
        adminFacade.currentUser();

        Log log = logRepository.findById(id).orElseThrow(
                () -> LogNotFountException.EXCEPTION);

        logRepository.delete(log);
    }
}
