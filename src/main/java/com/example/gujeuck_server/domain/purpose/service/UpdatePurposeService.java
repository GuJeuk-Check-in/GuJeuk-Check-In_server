package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.purpose.dto.request.PurposeMoveRequest;
import com.example.gujeuck_server.domain.purpose.dto.request.PurposeRequest;
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.purpose.exception.PurposeNotFoundException;
import com.example.gujeuck_server.domain.purpose.repository.PurposeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UpdatePurposeService {
    private final PurposeRepository purposeRepository;
    private final AdminFacade adminFacade;

    @Transactional
    public void updatePurpose(Long id, PurposeRequest purposeRequest) {
        adminFacade.currentUser();

        Purpose purpose = purposeRepository.findById(id)
                .orElseThrow(() -> PurposeNotFoundException.EXCEPTION);

        purpose.updatePurpose(purposeRequest.getPurpose());
    }

    @Transactional
    public void movementPurpose(PurposeMoveRequest purposeMoveRequest) {
        adminFacade.currentUser();

        List<Purpose> purposes = purposeRepository.findAllById(purposeMoveRequest.getPurposeId());

        Map<Long, Purpose> purposeMap = purposes.stream()
                .collect(Collectors.toMap(Purpose::getId, purpose -> purpose));

        for(int i = 0; i < purposeMoveRequest.getPurposeId().size(); i++) {
            Purpose purpose = purposeMap.get(purposeMoveRequest.getPurposeId().get(i));

            purpose.setPurposeIndex(i + 1);
        }
    }
}
