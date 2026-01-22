package com.example.gujeuck_server.domain.user.service;


import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.exception.InvalidResidenceException;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
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
    private final OrganFacade organFacade;

    private static final String ETC = "기타";

    @Transactional(readOnly = true)
    public SliceWithTotalResponse<UserInfoResponse> execute(String residence, Pageable p) {

        Organ organ = organFacade.currentUser();

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

            long total = userRepository.countByOrganIdAndResidenceNotIn(organ.getId(), registeredResidences);

            Slice<UserInfoResponse> slice = userRepository.findByOrganIdAndResidenceNotIn(organ.getId(), registeredResidences, pageable)
                    .map(UserInfoResponse::from);

            return new SliceWithTotalResponse<>(total, slice);
        }

        Residence matched = Residence.fromKoreanName(data);

        if (matched != null) {
            String kr = matched.getKoreanName();
            long total = userRepository.countByResidenceAndOrganId(kr, organ.getId());

            Slice<UserInfoResponse> slice = userRepository.findByResidenceAndOrganId(kr, organ.getId(), pageable)
                    .map(UserInfoResponse::from);

            return new SliceWithTotalResponse<>(total, slice);
        }

        throw InvalidResidenceException.EXCEPTION;
    }
}
