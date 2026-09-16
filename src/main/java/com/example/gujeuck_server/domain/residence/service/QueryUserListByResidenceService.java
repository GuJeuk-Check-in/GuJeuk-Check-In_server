package com.example.gujeuck_server.domain.residence.service;

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

    private static final String ETC = "기타";

    @Transactional(readOnly = true)
    public UserSliceWithTotalResponse execute(Long organId, String residence, Pageable p) {
        Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        String data = residence.trim();

        if (ETC.equals(data)) {
            List<String> registeredResidences = residenceRepository.findAllResidenceNameByOrganId(organId);

            long total = userRepository.countByOrganIdAndResidenceNotIn(organId, registeredResidences);

            Slice<UserInfoResponse> slice = userRepository.findByOrganIdAndResidenceNotIn(organId, registeredResidences, pageable)
                    .map(UserInfoResponse::from);

            return new UserSliceWithTotalResponse(total, slice);
        }

        Residence matched = residenceRepository.findByResidenceNameAndOrganId(data, organId)
                .orElseThrow(() -> ResidenceNotFoundException.EXCEPTION);

        String rn = matched.getResidenceName();
        long total = userRepository.countByResidenceAndOrganId(rn, organId);

        Slice<UserInfoResponse> slice = userRepository.findByResidenceAndOrganId(rn, organId, pageable)
            .map(UserInfoResponse::from);

        return new UserSliceWithTotalResponse(total, slice);
    }
}
