package com.example.gujeuck_server.domain.admin.service;

import com.example.gujeuck_server.domain.admin.dto.request.UseListRequest;
import com.example.gujeuck_server.domain.admin.service.facade.AdminFacade;
import com.example.gujeuck_server.domain.log.entity.Log;
import com.example.gujeuck_server.domain.log.repository.LogRepository;
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.NotFoundPurposeException;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateUseListService {
    private final LogRepository logRepository;
    private final PurposeRepository purposeRepository;
    private final AdminFacade adminFacade;

    @Transactional
    public void creatUseList(UseListRequest useListRequest) {

        adminFacade.currentUser();

        int currentYear = LocalDate.now().getYear();

        Purpose purpose = purposeRepository.findByPurpose(useListRequest.getPurpose())
                .orElseThrow(() -> NotFoundPurposeException.EXCEPTION);

        logRepository.save(Log.builder()
                .name(useListRequest.getName())
                .age(useListRequest.getAge())
                .phone(useListRequest.getPhone())
                .maleCount(useListRequest.getMaleCount())
                .femaleCount(useListRequest.getFemaleCount())
                .purpose(purpose.getPurpose())
                .visitDate(useListRequest.getVisitDate())
                .year(currentYear)
                .privacyAgreed(useListRequest.isPrivacyAgreed())
                .build());
    }
}
