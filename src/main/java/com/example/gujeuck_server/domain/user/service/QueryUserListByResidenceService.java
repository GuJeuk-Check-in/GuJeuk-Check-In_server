package com.example.gujeuck_server.domain.user.service;


import com.example.gujeuck_server.domain.admin.domain.Admin;
import com.example.gujeuck_server.domain.admin.exception.InvalidResidenceException;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.user.presentation.dto.response.SliceWithTotalResponse;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserInfoResponse;
import com.example.gujeuck_server.domain.user.domain.enums.Residence;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryUserListByResidenceService {

    private final UserRepository userRepository;
    private final AdminFacade adminFacade;

    private static final String ETC = "기타";

    @Transactional(readOnly = true)
    public SliceWithTotalResponse<UserInfoResponse> readAllUserListByResidence(String residence, Pageable p) {

        Admin admin = adminFacade.currentUser();

        Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        String data = residence.trim();

        if (ETC.equals(data)) {
            List<String> registeredResidences = Arrays.stream(Residence.values())
                    .map(Residence::getKoreanName)
                    .toList();

            long total = userRepository.countByAdminIdAndResidenceNotIn(admin.getId(), registeredResidences);

            Slice<UserInfoResponse> slice = userRepository.findByAdminIdAndResidenceNotIn(admin.getId(), registeredResidences, pageable)
                    .map(UserInfoResponse::from);

            return new SliceWithTotalResponse<>(total, slice);
        }

        Residence matched = Residence.fromKoreanName(data);

        if (matched != null) {
            String kr = matched.getKoreanName();
            long total = userRepository.countByResidenceAndAdminId(kr, admin.getId());

            Slice<UserInfoResponse> slice = userRepository.findByResidenceAndAdminId(kr, admin.getId(), pageable)
                    .map(UserInfoResponse::from);

            return new SliceWithTotalResponse<>(total, slice);
        }

        throw InvalidResidenceException.EXCEPTION;
    }
}