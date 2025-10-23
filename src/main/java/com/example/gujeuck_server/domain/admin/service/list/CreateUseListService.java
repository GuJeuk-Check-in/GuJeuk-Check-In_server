package com.example.gujeuck_server.domain.admin.service.list;

import com.example.gujeuck_server.domain.admin.dto.request.UseListRequest;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.log.entity.Log;
import com.example.gujeuck_server.domain.log.repository.LogRepository;
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.NotFoundPurposeException;
import com.example.gujeuck_server.domain.purpose.exception.PurposeNotFoundException;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;

import com.example.gujeuck_server.global.utility.DateFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

        String visitTime = LocalTime.now().format(DateTimeFormatter.ofPattern(TIME));
        int currentYear = LocalDate.now().getYear();

        List<Purpose> purposeList = purposeRepository.findByPurpose(useListRequest.getPurpose());
        if (purposeList.isEmpty()) {
            throw PurposeNotFoundException.EXCEPTION;
        }

        Purpose purpose = purposeList.get(0);

        String formattedDate;
        if (useListRequest.getVisitDate().matches("\\d{4}년\\d{2}월\\d{2}일")) { //하드 코딩
            formattedDate = useListRequest.getVisitDate();
        } else {
            formattedDate = DateFormatter.LocalDateForm(LocalDate.now());
        }

        logRepository.save(Log.builder()
                .name(useListRequest.getName())
                .age(useListRequest.getAge())
                .phone(useListRequest.getPhone())
                .maleCount(useListRequest.getMaleCount())
                .femaleCount(useListRequest.getFemaleCount())
                .purpose(purpose.getPurpose())
                .visitTime(visitTime)
                .visitDate(formattedDate)
                .year(currentYear)
                .privacyAgreed(useListRequest.isPrivacyAgreed())
                .build());
    }

}
