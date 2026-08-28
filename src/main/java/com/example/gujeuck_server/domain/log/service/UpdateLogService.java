package com.example.gujeuck_server.domain.log.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.facade.LogFacade;
import com.example.gujeuck_server.global.utility.DateFormatter;
import com.example.gujeuck_server.domain.log.presentation.dto.request.LogRequest;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateLogService {
    private final LogFacade logFacade;
    private final PurposeFacade purposeFacade;

    @Transactional
    public void execute(Long organId, Long logId, LogRequest request) {
        Log log = logFacade.getLogByIdAndOrganId(logId, organId);
        String name = request.name().trim();
        Purpose purpose = purposeFacade.getPurpose(organId, request.purpose().trim());
        String visitDate = request.visitDate();
        String visitTime = request.visitTime();
        LocalDateTime visitAt = DateFormatter.toVisitAt(visitDate, visitTime);

        // 고치는 기록 자신은 검사에서 뺀다. 그렇지 않으면 다른 필드만 바꿔도
        // 자기 자신이 중복으로 잡혀 저장할 수 없다.
        logFacade.validateNotDuplicated(
                organId, name, request.age(), purpose.getPurposeName(), visitAt, logId);

        log.updateLog(
                name,
                request.age(),
                request.phone(),
                request.maleCount(),
                request.femaleCount(),
                purpose.getPurposeName(),
                visitDate,
                visitTime,
                visitAt,
                request.privacyAgreed()
        );
    }
}
