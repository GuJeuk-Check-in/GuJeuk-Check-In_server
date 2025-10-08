package com.example.gujeuck_server.domain.admin.service;

import com.example.gujeuck_server.domain.admin.dto.UseListRequest;
import com.example.gujeuck_server.domain.log.entity.Log;
import com.example.gujeuck_server.domain.log.repository.LogRepository;
<<<<<<< HEAD
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.NotFoundPurposeException;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;
=======
>>>>>>> origin/feat/admin-CreateUseList
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
<<<<<<< HEAD
public class CreateUseListService {
    private final LogRepository logRepository;
    private final PurposeRepository purposeRepository;

    public void creatUseList(UseListRequest useListRequest) {
        Purpose purpose = purposeRepository.findById(useListRequest.getPurposeId())
                .orElseThrow(() -> NotFoundPurposeException.EXCEPTION);

        Log log = Log.builder()
                .name(useListRequest.getName())
                .age(useListRequest.getAge())
                .phone(useListRequest.getPhone())
                .maleCount(useListRequest.getMaleCount())
                .femaleCount(useListRequest.getFemaleCount())
                .purpose(purpose)
                .visitDate(useListRequest.getVisitDate())
                .build();
        logRepository.save(log);
    }

=======
public class CreateUseListService { 
    private final LogRepository logRepository;

    public void createUseList(UseListRequest useListRequest) {
        Log log = Log.builder()
                .leaderName(useListRequest.getLeaderName())
                .leaderAge(useListRequest.getLeaderAge())
                .maleCount(useListRequest.getMaleCount())
                .femaleCount(useListRequest.getFemaleCount())
                .companionList(useListRequest.getCompanionList())
                .purpose(useListRequest.getPurpose())
                .visitDate(useListRequest.getVisitDate())
                .privacyAgreed(useListRequest.isPrivacyAgreed())
                .build();
        logRepository.save(log);
    }
>>>>>>> origin/feat/admin-CreateUseList
}
