package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.admin.domain.Admin;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.exception.ExistUserIdException;
import com.example.gujeuck_server.domain.user.exception.UserNotFoundException;
import com.example.gujeuck_server.domain.user.presentation.dto.request.UpdateUserRequest;
import com.example.gujeuck_server.global.utility.CalculateAgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserService {
    private final AdminFacade adminFacade;
    private final UserRepository userRepository;
    private final CalculateAgeService calculateAgeService;

    @Transactional
    public void execute(Long id, UpdateUserRequest request) {
        Admin admin = adminFacade.currentUser();

        User user = userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        userRepository.findByUserIdAndAdminId(request.userId(), admin.getId())
                .filter(existUser -> !existUser.getId().equals(id))
                .ifPresent(existUser -> {
                    throw ExistUserIdException.EXCEPTION;
                });

        Age age = calculateAgeService.getAge(request.birthYMD());

        user.updateUser(request.name(), request.userId(), request.phone(), request.birthYMD(), request.residence());
    }
}
