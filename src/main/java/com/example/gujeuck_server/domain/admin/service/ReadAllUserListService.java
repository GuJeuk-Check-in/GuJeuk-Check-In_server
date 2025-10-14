package com.example.gujeuck_server.domain.admin.service;

import com.example.gujeuck_server.domain.admin.service.facade.AdminFacade;
import com.example.gujeuck_server.domain.user.dto.UserResponse;
import com.example.gujeuck_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllUserListService {
    private final UserRepository userRepository;
    private final AdminFacade adminFacade;

    @Transactional(readOnly = true)
    public List<UserResponse> readAllUserList() {
        adminFacade.currentUser();

        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .toList();
    }
}
