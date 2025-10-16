package com.example.gujeuck_server.domain.admin.service.list;


import com.example.gujeuck_server.domain.user.dto.response.UserResponse;
import com.example.gujeuck_server.domain.user.entity.enums.Residence;
import com.example.gujeuck_server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadAllUserListByResidenceService {
    private final UserRepository userRepository;

    public List<UserResponse> readAllUserListByResidence(String residence) {
        Residence matched = Residence.fromKoreanName(residence);

        if (matched != null) {
            return userRepository.findByResidence(matched.getKoreanName())
                    .stream()
                    .map(UserResponse::from)
                    .toList();
        }

        List<String> registeredResidences = Arrays.stream(Residence.values())
                .map(Residence::getKoreanName)
                .toList();

        return userRepository.findByResidenceNotIn(registeredResidences)
                .stream()
                .map(UserResponse::from)
                .toList();
    }
}
