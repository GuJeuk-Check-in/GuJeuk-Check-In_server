package com.example.gujeuck_server.domain.organ.service;

import com.example.gujeuck_server.domain.organ.exception.InvalidUserNameException;
import com.example.gujeuck_server.domain.organ.exception.UserNameNotFoundException;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.UserWithTotalResponse;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.user.UserListResponse;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.user.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryListByNameService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserWithTotalResponse execute(String name, Pageable p) {
        if (name == null || name.isBlank() || name.length() > 30) {
            throw InvalidUserNameException.EXCEPTION;
        }

        Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.ASC, "id")
        );

        long total = userRepository.countByOrganIdAndNameContaining(name);

        if (total == 0) {
            return UserWithTotalResponse.of(0, List.of());
        }


        Slice<UserListResponse> slice = userRepository.findAllByOrganIdAndNameContainingOrderByIdAsc(name, pageable)
                .map(UserListResponse::from);

        return UserWithTotalResponse.of(
                total, slice.getContent()
        );


    }
}
