package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.admin.domain.Admin;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
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
    private final AdminFacade adminFacade;

    @Transactional(readOnly = true)
    public UserDetailResponse execute(Long id) {
        Admin admin = adminFacade.currentUser();

        User user = userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (!user.getAdmin().getId().equals(admin.getId())) {
            throw UserAccessDeniedException.EXCEPTION;
        }

        return UserDetailResponse.from(user);
    }
}
