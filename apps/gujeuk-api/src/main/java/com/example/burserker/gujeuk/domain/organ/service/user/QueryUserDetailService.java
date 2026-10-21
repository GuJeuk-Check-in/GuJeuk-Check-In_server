package com.example.burserker.gujeuk.domain.organ.service.user;

import com.example.burserker.gujeuk.domain.user.domain.User;
import com.example.burserker.gujeuk.domain.user.domain.repository.UserRepository;
import com.example.burserker.gujeuk.domain.user.exception.UserAccessDeniedException;
import com.example.burserker.gujeuk.domain.user.exception.UserNotFoundException;
import com.example.burserker.gujeuk.domain.organ.presentation.dto.response.user.UserDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueryUserDetailService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDetailResponse execute(Long organId, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        if (!user.getOrgan().getId().equals(organId)) {
            throw UserAccessDeniedException.EXCEPTION;
        }

        return UserDetailResponse.from(user);
    }
}
