package com.example.gujeuck_server.domain.User.service;

import com.example.gujeuck_server.domain.User.dto.UserResponse;
import com.example.gujeuck_server.domain.User.entity.User;
import com.example.gujeuck_server.domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadOneUserListService {
    private final UserRepository userRepository;

    public UserResponse readOneUserList(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("해당 회원이 존재하지 않습니다.")
        );

        return new UserResponse(user.getId(), user.getUserId(), user.getName(), user.getPhone(), user.getGender(), user.getBirthYMD(), user.getResidence(), user.isPrivacyAgreed());
    }
}
