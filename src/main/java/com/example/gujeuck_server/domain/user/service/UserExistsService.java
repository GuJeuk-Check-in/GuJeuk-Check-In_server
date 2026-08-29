package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.domain.repository.LogRepository;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.request.UserExistsRequest;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserExistsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserExistsService {

    private final UserRepository userRepository;
    private final LogRepository logRepository;

    @Transactional(readOnly = true)
    public UserExistsResponse execute(UserExistsRequest request) {
        List<User> namesakes = userRepository.findAllByName(request.name()).stream()
                .sorted(Comparator.comparing(User::getId))
                .toList();

        if(namesakes.isEmpty()){
            return UserExistsResponse.of(false, null);
        }

        User target = pickNextInRotation(namesakes, request.name());

        return UserExistsResponse.of(true, target.getId());
    }

    private User pickNextInRotation(List<User> namesakes, String name) {
        Long lastUserId = logRepository.findFirstByNameAndUserIsNotNullOrderByIdDesc(name)
                .map(Log::getUser)
                .map(User::getId)
                .orElse(null);

        if (lastUserId == null) {
            return namesakes.get(0);
        }

        int lastIndex = indexOfUserId(namesakes, lastUserId);

        if (lastIndex < 0) {
            return namesakes.get(0);
        }

        return namesakes.get((lastIndex + 1) % namesakes.size());
    }

    private int indexOfUserId(List<User> namesakes, Long userId) {
        for (int i = 0; i < namesakes.size(); i++) {
            if (namesakes.get(i).getId().equals(userId)) {
                return i;
            }
        }
        return -1;
    }
}
