package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
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

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        Organ organ = user.getOrgan();

        // 등록된 방문목적인지 검증하고 정규화된 이름을 사용한다.
        Purpose purpose = purposeFacade.getPurpose(organ.getId(), request.getPurpose());

        LocalDateTime visitDateTime = request.getVisitTime();
        log.info("포멧팅 하기 전 시각 : ", visitDateTime.toString());
        String visitDate = DateFormatter.toVisitDate(visitDateTime);
        String visitTime = DateFormatter.toVisitTime(visitDateTime);
        log.info("포멧팅 한 후의 날짜 : ", visitDate);
        log.info("포멧팅 한 후의 시간 : ", visitTime);
        int year = visitDateTime.getYear();

        // 같은 유저가 같은 시각에 이미 체크인했는지 확인한다.
        if (logRepository.findByUserIdAndVisitTime(user.getId(), visitDate, visitTime).isPresent()) {
            throw DuplicateLogException.EXCEPTION;
        }

        user.increaseCount();

        Log log = createLog(user, organ, purpose.getPurposeName(), request, visitDate, visitTime, year);

        logRepository.save(log);
    }

    private Log createLog(
            User user,
            Organ organ,
            String purpose,
            UserCheckInRequest request,
            String visitDate,
            String visitTime,
            int year
    ) {
        return Log.builder()
                .name(user.getName())
                .phone(user.getPhone())
                .age(user.getAge())
                .maleCount(request.getMaleCount())
                .femaleCount(request.getFemaleCount())
                .purpose(purpose)
                .privacyAgreed(user.isPrivacyAgreed())
                .visitDate(visitDate)
                .visitTime(visitTime)
                .year(year)
                .user(user)
                .organ(organ)
                .build();
    }
}
