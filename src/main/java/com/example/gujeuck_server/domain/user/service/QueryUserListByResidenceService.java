package com.example.gujeuck_server.domain.user.service;


import com.example.gujeuck_server.domain.admin.exception.InvalidResidenceException;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserDto;
import com.example.gujeuck_server.domain.user.domain.enums.Residence;
import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import com.example.gujeuck_server.domain.user.presentation.dto.response.UserListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryUserListByResidenceService {

    private final UserRepository userRepository;
    private final AdminFacade adminFacade;

    private static final String ETC = "기타";

    @Transactional(readOnly = true)
    public UserListResponse readAllUserListByResidence(String residence) {
        adminFacade.currentUser();

        String data = residence.trim();

        if (ETC.equals(data)) {
            List<String> registeredResidences = Arrays.stream(Residence.values())
                    .map(Residence::getKoreanName)
                    .toList();

            long total = userRepository.countByResidenceNotIn(registeredResidences);

            List<UserDto> users = userRepository.findByResidenceNotIn(registeredResidences)
                    .stream()
                    .map(UserDto::from)
                    .toList();

            return UserListResponse.builder()
                        .totalCount(total)
                        .users(users)
                    .build();
        }

        Residence matched = Residence.fromKoreanName(data);

        if (matched != null) {
            String kr = matched.getKoreanName();
            long total = userRepository.countByResidence(kr);

            List<UserDto> users = userRepository.findByResidence(kr)
                    .stream()
                    .map(UserDto::from)
                    .toList();

            return UserListResponse.builder()
                        .totalCount(total)
                        .users(users)
                    .build();
        }

        throw InvalidResidenceException.EXCEPTION;
    }
}