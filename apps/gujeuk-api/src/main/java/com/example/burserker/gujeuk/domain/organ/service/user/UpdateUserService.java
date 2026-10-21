package com.example.burserker.gujeuk.domain.organ.service.user;

import com.example.burserker.gujeuk.domain.user.domain.User;
import com.example.burserker.gujeuk.domain.user.domain.enums.Age;
import com.example.burserker.gujeuk.domain.user.domain.repository.UserRepository;
import com.example.burserker.gujeuk.domain.user.exception.UserAccessDeniedException;
import com.example.burserker.gujeuk.domain.user.exception.UserNotFoundException;
import com.example.burserker.gujeuk.domain.organ.presentation.dto.request.user.UpdateUserRequest;
import com.example.burserker.gujeuk.global.utility.CalculateAgeService;
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
