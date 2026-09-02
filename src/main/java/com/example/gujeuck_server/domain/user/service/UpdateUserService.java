package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.exception.UserAccessDeniedException;
import com.example.gujeuck_server.domain.user.exception.UserNotFoundException;
import com.example.gujeuck_server.domain.user.presentation.dto.request.UpdateUserRequest;
import com.example.gujeuck_server.global.utility.CalculateAgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserService {
    private final UserRepository userRepository;
    private final CalculateAgeService calculateAgeService;

    @Transactional
    public void execute(Long organId, Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (!organId.equals(user.getOrgan().getId())) {
            throw UserAccessDeniedException.EXCEPTION;
        }

        Age age = calculateAgeService.getAge(request.birthYMD());

        user.updateUser(
                request.name(),
                request.phone(),
                request.birthYMD(),
                request.residence(),
                request.gender(),
                age
        );
    }
}
