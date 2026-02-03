package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.user.presentation.dto.response.SliceWithTotalResponse;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserInfoResponse;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryUserListService {
    private final UserRepository userRepository;
    private final OrganFacade organFacade;

    @Transactional(readOnly = true)
    public SliceWithTotalResponse<UserInfoResponse> execute(Pageable p) {
        Organ organ = organFacade.currentOrgan();

        Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        long total = userRepository.countByOrganId(organ.getId());

        Slice<UserInfoResponse> slice = userRepository.findAllByOrganId(pageable, organ.getId())
                .map(UserInfoResponse::from);

        return new UserSliceWithTotalResponse(total, slice);
    }
}
