package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.log.exception.ClientRecordIdMismatchException;
import com.example.gujeuck_server.domain.log.exception.DuplicateLogException;
import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.facade.PurposeFacade;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.exception.UserNotFoundException;
import com.example.gujeuck_server.domain.user.presentation.dto.request.UserCheckInRequest;
import com.example.gujeuck_server.global.utility.DateFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.SortArgumentResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.example.gujeuck_server.domain.log.domain.QLog.log;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCheckInService {

    private final UserRepository userRepository;
    private final LogRepository logRepository;
    private final PurposeFacade purposeFacade;
    private final SortArgumentResolver sortArgumentResolver;

    @Transactional
    public void execute(UserCheckInRequest request) {

        // HA 경로의 재요청(네트워크 실패 후 재전송 등)은 clientRecordId로 이미 처리된 요청인지 먼저 확인해
        // 기존 중복검사 없이 멱등하게 성공 처리한다. 단, 같은 clientRecordId인데 요청 내용이 다르면
        // 클라이언트 쪽 ID 재사용 버그일 수 있으므로 정합성 오류로 막는다.
        if (request.clientRecordId() != null) {
            Optional<Log> existingLog = logRepository.findByClientRecordId(request.clientRecordId());
            if (existingLog.isPresent()) {
                if (isSameRequest(existingLog.get(), request)) {
                    return;
                }
                throw ClientRecordIdMismatchException.EXCEPTION;
            }
        }

        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        Organ organ = user.getOrgan();

        // 등록된 방문목적인지 검증하고 정규화된 이름을 사용한다.
        Purpose purpose = purposeFacade.getPurpose(organ.getId(), request.purpose());

        LocalDateTime visitDateTime = request.visitTime();
        log.info("포멧팅 하기 전 시각 : ", visitDateTime.toString());
        String visitDate = DateFormatter.toVisitDate(visitDateTime);
        String visitTime = DateFormatter.toVisitTime(visitDateTime);
        log.info("포멧팅 한 후의 날짜 : ", visitDate);
        log.info("포멧팅 한 후의 시간 : ", visitTime);
        int year = visitDateTime.getYear();

        // 같은 유저가 같은 순간(초 단위)에 이미 체크인했는지 확인한다.
        if (logRepository.findByUserIdAndVisitAt(user.getId(), visitDateTime, purpose.getPurposeName()).isPresent()) {
            throw DuplicateLogException.EXCEPTION;
        }

        user.increaseCount();

        Log log = createLog(user, organ, purpose.getPurposeName(), request, visitDate, visitTime, visitDateTime, year);

        logRepository.save(log);
    }

    private boolean isSameRequest(Log existingLog, UserCheckInRequest request) {
        Long existingUserId = existingLog.getUser() != null ? existingLog.getUser().getId() : null;

        return Objects.equals(existingUserId, request.userId())
                && existingLog.getPurpose().equals(request.purpose())
                && existingLog.getVisitAt().equals(request.visitTime())
                && existingLog.getMaleCount() == request.maleCount()
                && existingLog.getFemaleCount() == request.femaleCount();
    }

    private Log createLog(
            User user,
            Organ organ,
            String purpose,
            UserCheckInRequest request,
            String visitDate,
            String visitTime,
            LocalDateTime visitAt,
            int year
    ) {
        return Log.builder()
                .name(user.getName())
                .phone(user.getPhone())
                .age(user.getAge())
                .maleCount(request.maleCount())
                .femaleCount(request.femaleCount())
                .purpose(purpose)
                .privacyAgreed(user.isPrivacyAgreed())
                .visitDate(visitDate)
                .visitTime(visitTime)
                .visitAt(visitAt)
                .year(year)
                .user(user)
                .organ(organ)
                .clientRecordId(request.clientRecordId())
                .build();
    }
}
