package com.example.gujeuck_server.domain.admin.service.list;


import com.example.gujeuck_server.domain.admin.exception.InvalidResidenceException;
import com.example.gujeuck_server.domain.admin.facade.AdminFacade;
import com.example.gujeuck_server.domain.user.dto.response.UserDto;
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
    private static final String ETC = "기타";
    private final AdminFacade adminFacade;

    public UserResponse readAllUserListByResidence(String residence) {
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

            return new UserResponse(total, users);
        }

        Residence matched = Residence.fromKoreanName(data);

        if (matched != null) {
            String kr = matched.getKoreanName();
            long total = userRepository.countByResidence(kr);

            List<UserDto> users = userRepository.findByResidence(kr)
                    .stream()
                    .map(UserDto::from)
                    .toList();

            return new UserResponse(total, users);
        }

        throw InvalidResidenceException.EXCEPTION;
    }
}