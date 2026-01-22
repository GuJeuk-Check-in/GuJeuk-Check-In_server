package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.facade.OrganFacade;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.exception.ExistUserIdException;
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
    private final OrganFacade organFacade;
    private final UserRepository userRepository;
    private final CalculateAgeService calculateAgeService;

    @Transactional
    public void execute(Long id, UpdateUserRequest request) {
        Organ organ = organFacade.currentUser();

        User user = userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (!organ.getId().equals(user.getOrgan().getId())) {
            throw UserAccessDeniedException.EXCEPTION;
        }

        userRepository.findByUserIdAndOrganId(request.userId(), organ.getId())
                .filter(existUser -> !existUser.getId().equals(id))
                .ifPresent(existUser -> {
                    throw ExistUserIdException.EXCEPTION;
                });

        Age age = calculateAgeService.getAge(request.birthYMD());

        user.updateUser(request.name(), request.userId(), request.phone(), request.birthYMD(), request.residence());
    }
}
