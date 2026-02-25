package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.log.exception.LogAccessDeniedException;
import com.example.gujeuck_server.domain.log.facade.LogFacade;
import com.example.gujeuck_server.domain.log.presentation.dto.request.LogRequest;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateLogService {
    private final LogFacade logFacade;
    private final OrganFacade organFacade;
    private final PurposeFacade purposeFacade;

    @Transactional
    public void execute(Long logId, LogRequest request) {
        Organ organ = organFacade.currentOrgan();

        Log log = logFacade.getLogById(logId);

        Purpose purpose = purposeFacade.getPurpose(organ.getId(), request.getPurpose());

        log.updateLog(
                request.getName(),
                request.getAge(),
                request.getPhone(),
                request.getMaleCount(),
                request.getFemaleCount(),
                purpose.getPurposeName(),
                request.getVisitDate(),
                request.isPrivacyAgreed()
        );
    }
}
