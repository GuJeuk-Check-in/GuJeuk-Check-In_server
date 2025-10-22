package com.example.gujeuck_server.domain.admin.service.list;

import com.example.gujeuck_server.domain.admin.dto.request.UseListRequest;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.log.entity.Log;
import com.example.gujeuck_server.domain.log.repository.LogRepository;
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.NotFoundPurposeException;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CreateUseListService {
    private final LogRepository logRepository;
    private final PurposeRepository purposeRepository;
    private final AdminFacade adminFacade;

    private static final String TIME = "HH:mm";

    @Transactional
    public void creatUseList(UseListRequest useListRequest) {

        adminFacade.currentUser();

        String visitTime = LocalTime.now().format(DateTimeFormatter.ofPattern(TIME)); // 객체 생성 시간에 맞게 시간 설정


        int currentYear = LocalDate.now().getYear();

        Purpose purpose = purposeRepository.findByPurpose(useListRequest.getPurpose())
                        .stream().toList().get(0);

        logRepository.save(Log.builder()
                .name(useListRequest.getName())
                .age(useListRequest.getAge())
                .phone(useListRequest.getPhone())
                .maleCount(useListRequest.getMaleCount())
                .femaleCount(useListRequest.getFemaleCount())
                .purpose(purpose.getPurpose())
                .visitTime(visitTime)
                .visitDate(useListRequest.getVisitDate())
                .year(currentYear)
                .privacyAgreed(useListRequest.isPrivacyAgreed())
                .build());
    }
}
