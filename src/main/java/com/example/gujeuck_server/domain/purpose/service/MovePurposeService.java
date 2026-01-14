package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import com.example.gujeuck_server.domain.purpose.domain.repository.PurposeRepository;
import com.example.gujeuck_server.domain.purpose.presentation.dto.request.PurposeMoveRequest;
import com.example.gujeuck_server.domain.purpose.exception.PurposeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovePurposeService {
    private final PurposeRepository purposeRepository;
    private final AdminFacade adminFacade;

    @Transactional
    public void movementPurpose(PurposeMoveRequest purposeMoveRequest) {
        adminFacade.currentUser();

        List<Long> purposesId = purposeMoveRequest.getPurposeId();
        List<Purpose> purposes = purposeRepository.findAllById(purposeMoveRequest.getPurposeId());

        if(purposesId.size() != purposes.size()) {
            throw PurposeNotFoundException.EXCEPTION;
        }

        Map<Long, Purpose> purposeMap = purposes.stream()
                .collect(Collectors.toMap(Purpose::getId, purpose -> purpose));

        for(int i = 0; i < purposeMoveRequest.getPurposeId().size(); i++) {
            Purpose purpose = purposeMap.get(purposeMoveRequest.getPurposeId().get(i));

            if(purpose == null) {
                throw PurposeNotFoundException.EXCEPTION;
            }

            purpose.setPurposeIndex(i + 1);
        }
    }
}
