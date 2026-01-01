package com.example.gujeuck_server.domain.admin.service.list;

import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteAllThingService {
    private final AdminFacade adminFacade;
    private final LogRepository logRepository;

    @Transactional
    public void deleteALl() {
        adminFacade.currentUser();

        logRepository.deleteAll();
    }
}
