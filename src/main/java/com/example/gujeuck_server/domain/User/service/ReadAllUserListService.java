package com.example.gujeuck_server.domain.User.service;

import com.example.gujeuck_server.domain.User.dto.UserResponse;
import com.example.gujeuck_server.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllUserListService {
    private final UserRepository userRepository;

    public List<UserResponse> readAllUserList() {
        return userRepository.findAll().stream()
                .map(userList -> new UserResponse(userList.getId(), userList.getName(), userList.getGender(), userList.getPhone(), userList.getBirthYMD(), userList.getResidence(), userList.isPrivacyAgreed()))
                .toList();
    }
}
