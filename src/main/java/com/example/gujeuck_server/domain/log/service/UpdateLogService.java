package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.exception.DuplicateLogException;
import com.example.gujeuck_server.domain.log.facade.LogFacade;
import com.example.gujeuck_server.domain.log.presentation.dto.request.LogRequest;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
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
    private final AdminFacade adminFacade;
    private final PurposeFacade purposeFacade;
    private final LogRepository logRepository;

    @Transactional
    public void execute(Long logId, LogRequest request) {
        adminFacade.currentUser();

        Log log = logFacade.getLogById(logId);

        Purpose purpose = purposeFacade.getPurpose(request.getPurpose());

        if(log.getUser() != null){
            validateDuplicateLog(log.getUser().getUserId(), log.getVisitDate(), log.getVisitTime());

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
        } else {
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

    private void validateDuplicateLog(String userId, String visitDate, String visitTime) {

        if (logRepository.findByUserIdAndVisitTime(userId, visitDate, visitTime).isPresent()) {
            throw DuplicateLogException.EXCEPTION;
        }
    }
}
