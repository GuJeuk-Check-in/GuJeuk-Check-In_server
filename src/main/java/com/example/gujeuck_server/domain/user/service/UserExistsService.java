package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.request.UserExistsRequest;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserExistsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserExistsService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserExistsResponse execute(UserExistsRequest request) {
        String normalizedPhone = normalizePhone(request.getPhone());

        return userRepository.findAllByNameAndNormalizedPhone(request.getName(), normalizedPhone).stream()
                .findFirst()
                .map(user -> UserExistsResponse.of(true, user.getId()))
                .orElseGet(() -> UserExistsResponse.of(false, null));
    }

    private String normalizePhone(String phone) {
        return phone == null ? "" : phone.replaceAll("\\D", "");
    }
}
