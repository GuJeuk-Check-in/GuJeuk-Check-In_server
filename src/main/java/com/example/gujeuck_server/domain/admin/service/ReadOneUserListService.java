package com.example.gujeuck_server.domain.admin.service;

import com.example.gujeuck_server.domain.admin.service.facade.AdminFacade;
import com.example.gujeuck_server.domain.user.dto.UserResponse;
import com.example.gujeuck_server.domain.user.entity.User;
import com.example.gujeuck_server.domain.user.exception.UserNotFoundException;
import com.example.gujeuck_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadOneUserListService {
    private final UserRepository userRepository;
    private final AdminFacade adminFacade;

    public UserResponse readOneUserList(Long id) {
        adminFacade.currentUser();

        User user = userRepository.findById(id).orElseThrow(
                () -> UserNotFoundException.EXCEPTION);

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .phone(user.getPhone())
                .birthYMD(user.getBirthYMD())
                .gender(user.getGender())
                .privacyAgreed(user.isPrivacyAgreed())
                .residence(user.getResidence())
                .build();
    }
}
