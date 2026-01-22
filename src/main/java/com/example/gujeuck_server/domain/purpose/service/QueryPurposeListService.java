package com.example.gujeuck_server.domain.purpose.service;

import com.example.gujeuck_server.domain.admin.domain.Admin;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.purpose.domain.repository.PurposeRepository;
import com.example.gujeuck_server.domain.purpose.presentation.dto.response.PurposeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryPurposeListService {
    private final PurposeRepository purposeRepository;
    private final AdminFacade adminFacade;

    @Transactional(readOnly = true)
    public List<PurposeResponse> execute() {

        Admin admin = adminFacade.currentUser();

        return purposeRepository.findAllByAdminIdOrderByPurposeIndexAsc(admin.getId()).stream()
                .map(PurposeResponse::from)
                .toList();
    }
}
