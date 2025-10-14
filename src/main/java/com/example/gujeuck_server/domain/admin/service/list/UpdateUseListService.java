package com.example.gujeuck_server.domain.admin.service.list;

import com.example.gujeuck_server.domain.admin.dto.request.UseListRequest;
import com.example.gujeuck_server.domain.admin.exception.LogNotFountException;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.log.entity.Log;
import com.example.gujeuck_server.domain.log.repository.LogRepository;
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.NotFoundPurposeException;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        Purpose purpose = purposeRepository.findByPurpose(useListRequest.getPurpose())
                .orElseThrow(() -> NotFoundPurposeException.EXCEPTION);


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
 