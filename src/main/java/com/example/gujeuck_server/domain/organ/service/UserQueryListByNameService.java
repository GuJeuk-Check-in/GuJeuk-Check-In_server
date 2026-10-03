package com.example.gujeuck_server.domain.organ.service;

import com.example.gujeuck_server.domain.organ.exception.InvalidUserNameException;
import com.example.gujeuck_server.domain.organ.exception.UserNameNotFoundException;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.UserWithTotalResponse;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.organ.presentation.dto.response.user.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserQueryListByNameService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserWithTotalResponse execute(Long organId, String name, Pageable p) {
        if (name == null || name.isBlank() || name.length() > 30) {
            throw InvalidUserNameException.EXCEPTION;
        }

        Pageable pageable = PageRequest.of(
                p.getPageNumber(),
                p.getPageSize(),
                Sort.by(Sort.Direction.ASC, "id")
        );

        long total = userRepository.countByOrganIdAndNameContaining(organId, name);

        if (total == 0) {
            throw UserNameNotFoundException.EXCEPTION;
        }

        Slice<UserInfoResponse> slice = userRepository.findAllByOrganIdAndNameContainingOrderByIdAsc(organId, name, pageable)
                .map(UserInfoResponse::from);

        return new UserWithTotalResponse(
                total, slice.getContent()
        );

    }
}
