package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.user.entity.User;
import com.example.gujeuck_server.domain.user.exception.ExistUserIdException;
import com.example.gujeuck_server.domain.user.repository.UserRepository;
import com.example.gujeuck_server.domain.user.dto.request.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final UserRepository userRepository;

    @Transactional
    public void signup(SignupRequest request) {
        String userId = request.getName() + request.getBirthYMD();

        if (userRepository.existsByUserId(userId)) {
            throw ExistUserIdException.EXCEPTION;
        }

        userRepository.save(User.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .gender(request.getGender())
                .birthYMD(request.getBirthYMD())
                .residence(request.getResidence())
                .privacyAgreed(request.isPrivacyAgreed())
                .userId(userId)
                .build());
    }
}
