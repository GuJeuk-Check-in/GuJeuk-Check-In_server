package com.example.gujeuck_server.domain.admin.service.list;

import com.example.gujeuck_server.domain.admin.presentation.dto.request.UseListRequest;
import com.example.gujeuck_server.domain.log.exception.LogNotFountException;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.PurposeNotFoundException;
import com.example.gujeuck_server.domain.purpose.domain.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateUseListService {
    private final LogRepository logRepository;
    private final PurposeRepository purposeRepository;
    private final AdminFacade adminFacade;

    @Transactional
    public void updateLog(Long id, UseListRequest useListRequest) {
        adminFacade.currentUser();

        Log log = logRepository.findById(id).orElseThrow(
                () -> LogNotFountException.EXCEPTION);

        List<Purpose> purposeList = purposeRepository.findByPurpose(useListRequest.getPurpose());

        if (purposeList.isEmpty()) {
            throw PurposeNotFoundException.EXCEPTION;
        }

        Purpose purpose = purposeList.get(0);

        log.updateLog(
                useListRequest.getName(),
                useListRequest.getAge(),
                useListRequest.getPhone(),
                useListRequest.getMaleCount(),
                useListRequest.getFemaleCount(),
                purpose.getPurpose(),
                useListRequest.getVisitDate(),
                useListRequest.isPrivacyAgreed()
        );
    }
}
