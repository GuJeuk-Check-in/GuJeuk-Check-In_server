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
        // 회원가입 시 방문 기록이 함께 생성되므로 보통은 직전 기록이 존재한다.
        // 다만 관리자가 이용 기록을 삭제하면(DeleteLogService) 기록이 0건이 될 수 있어,
        // 그 경우에는 예외 대신 첫 번째 사람부터 순번을 다시 시작한다.
        // 이번 방문으로 기록이 다시 쌓이면 순번은 자연히 복구된다.
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
