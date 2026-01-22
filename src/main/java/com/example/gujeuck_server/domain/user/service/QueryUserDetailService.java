package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.exception.UserAccessDeniedException;
import com.example.gujeuck_server.domain.user.exception.UserNotFoundException;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryUserDetailService {
    private final UserRepository userRepository;
    private final OrganFacade organFacade;

    @Transactional(readOnly = true)
    public UserDetailResponse execute(Long id) {
        Organ organ = organFacade.currentUser();

        User user = userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (!user.getOrgan().getId().equals(organ.getId())) {
            throw UserAccessDeniedException.EXCEPTION;
        }

        return UserDetailResponse.from(user);
    }
}
