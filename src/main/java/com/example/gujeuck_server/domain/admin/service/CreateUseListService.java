package com.example.gujeuck_server.domain.admin.service;

import com.example.gujeuck_server.domain.admin.dto.request.UseListRequest;
import com.example.gujeuck_server.domain.log.entity.Log;
import com.example.gujeuck_server.domain.log.repository.LogRepository;
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.NotFoundPurposeException;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUseListService {
    private final LogRepository logRepository;
    private final PurposeRepository purposeRepository;

    public void creatUseList(UseListRequest useListRequest) {

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
                .privacyAgreed(useListRequest.isPrivacyAgreed())
                .build());
    }
}
