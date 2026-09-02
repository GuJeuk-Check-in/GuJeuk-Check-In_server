package com.example.gujeuck_server.domain.user.service;

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

@Service
@RequiredArgsConstructor
public class QueryUserListService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserSliceWithTotalResponse execute(Long organId, Pageable p) {
        Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );

        long total = userRepository.countByOrganId(organId);

        Slice<UserInfoResponse> slice = userRepository.findAllByOrganId(pageable, organId)
                .map(UserInfoResponse::from);

        return new UserSliceWithTotalResponse(total, slice);
    }
}
