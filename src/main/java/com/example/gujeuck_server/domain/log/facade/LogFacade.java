package com.example.gujeuck_server.domain.log.facade;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.exception.DuplicateLogException;
import com.example.gujeuck_server.domain.user.domain.enums.Age;

import java.time.LocalDateTime;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.exception.LogNotFountException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogFacade {
    private final LogRepository logRepository;

    public Log getLogByIdAndOrganId(Long logId, Long organId) {
        return logRepository.findByIdAndOrganId(logId, organId)
                .orElseThrow(() -> LogNotFountException.EXCEPTION);
    }

    /**
     * 같은 방문이 이미 있으면 막는다.
     *
     * 판정 기준은 visitAt(초 단위)이다. 분 단위 문자열로 보면 같은 분에 초만
     * 다른 방문이 중복으로 막힌다.
     *
     * 수정할 때는 고치는 기록 자신이 검사에 걸리므로 excludedId 로 빼야 한다.
     * 생성할 때는 뺄 대상이 없으니 null 을 넘긴다.
     */
    public void validateNotDuplicated(
            Long organId,
            String name,
            Age age,
            String purpose,
            LocalDateTime visitAt,
            Long excludedId
    ) {
        if (logRepository.existsDuplicate(organId, name, age, purpose, visitAt, excludedId)) {
            throw DuplicateLogException.EXCEPTION;
        }
    }
}
