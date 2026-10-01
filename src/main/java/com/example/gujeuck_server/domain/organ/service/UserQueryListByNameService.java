package com.example.gujeuck_server.domain.organ.service;

import com.example.gujeuck_server.domain.organ.presentation.dto.response.UserWithTotalResponse;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserInfoResponse;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserSliceWithTotalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserQueryListByNameService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserSliceWithTotalResponse execute(Long organId, String name, Pageable p) {
        Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.ASC, "id")
        );

        long total = userRepository.countByOrganIdAndNameContaining(organId, name);

        Slice<UserInfoResponse> slice = userRepository.findAllByOrganIdAndNameContainingOrderByIdAsc(organId, name, pageable)
                .map(UserInfoResponse::from);

        return new UserSliceWithTotalResponse(
                total, slice
        );

    }
}
