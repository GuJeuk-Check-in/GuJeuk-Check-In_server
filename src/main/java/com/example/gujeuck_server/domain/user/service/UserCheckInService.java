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

        Purpose purpose = purposeFacade.getPurpose(organ.getId(), request.purpose());

        LocalDateTime visitDateTime = request.visitTime();
        log.info("포멧팅 하기 전 시각 : ", visitDateTime.toString());
        String visitDate = DateFormatter.toVisitDate(visitDateTime);
        String visitTime = DateFormatter.toVisitTime(visitDateTime);
        log.info("포멧팅 한 후의 날짜 : ", visitDate);
        log.info("포멧팅 한 후의 시간 : ", visitTime);
        int year = visitDateTime.getYear();

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
