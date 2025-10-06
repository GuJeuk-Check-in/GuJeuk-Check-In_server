package com.example.gujeuck_server.domain.User.service;

<<<<<<< Updated upstream
import com.example.gujeuck_server.domain.user.dto.UserResponse;
import com.example.gujeuck_server.domain.user.repository.UserRepository;
=======
import com.example.gujeuck_server.domain.User.dto.UserResponse;
import com.example.gujeuck_server.domain.User.repository.UserRepository;
>>>>>>> Stashed changes
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllUserListService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<UserResponse> readAllUserList() {
        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .toList();
    }
}
