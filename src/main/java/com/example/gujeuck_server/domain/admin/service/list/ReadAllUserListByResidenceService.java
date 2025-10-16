package com.example.gujeuck_server.domain.admin.service.list;


import com.example.gujeuck_server.domain.user.dto.response.UserResponse;
import com.example.gujeuck_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllUserListByResidenceService {
    private final UserRepository userRepository;

    public List<UserResponse> readAllUserListByResidence(String residence) {
        return userRepository.findByResidence(residence)
                .map(UserResponse::from)
                .toList();
    }
}
