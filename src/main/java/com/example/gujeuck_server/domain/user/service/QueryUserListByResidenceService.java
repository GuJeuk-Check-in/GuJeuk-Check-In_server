package com.example.gujeuck_server.domain.user.service;


import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.exception.InvalidResidenceException;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.residence.domain.Residence;
import com.example.gujeuck_server.domain.residence.domain.repository.ResidenceRepository;
import com.example.gujeuck_server.domain.residence.exception.ResidenceNotFoundException;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserInfoResponse;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserSliceWithTotalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryUserListByResidenceService {

    private final UserRepository userRepository;
    private final ResidenceRepository residenceRepository;
    private final OrganFacade organFacade;

    private static final String ETC = "기타";

    @Transactional(readOnly = true)
    public UserSliceWithTotalResponse execute(String residence, Pageable p) {

        Organ organ = organFacade.currentOrgan();

        Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        String data = residence.trim();

        if (ETC.equals(data)) {
            List<String> registeredResidences = residenceRepository.findAllResidenceNamesByOrganId(organ.getId());

            long total = userRepository.countByOrganIdAndResidenceNotIn(organ.getId(), registeredResidences);

            Slice<UserInfoResponse> slice = userRepository.findByOrganIdAndResidenceNotIn(organ.getId(), registeredResidences, pageable)
                    .map(UserInfoResponse::from);

            return new UserSliceWithTotalResponse(total, slice);
        }

        Residence matched = residenceRepository.findByResidenceNameAndOrganId(data, organ.getId())
                .orElseThrow(() -> ResidenceNotFoundException.EXCEPTION);

        if (matched != null) {
            String rn = matched.getResidenceName();
            long total = userRepository.countByResidenceAndOrganId(rn, organ.getId());

            Slice<UserInfoResponse> slice = userRepository.findByResidenceAndOrganId(rn, organ.getId(), pageable)
                    .map(UserInfoResponse::from);

            return new UserSliceWithTotalResponse(total, slice);
        }

        throw InvalidResidenceException.EXCEPTION;
    }
}
