package com.example.gujeuck_server.domain.organ.service;

import com.example.gujeuck_server.domain.organ.exception.OrganAlreadyExistException;
import com.example.gujeuck_server.domain.organ.presentation.dto.request.OrganRequest;
import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.organ.domain.repository.OrganRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateOrganService {
    private final OrganRepository organRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(OrganRequest request) {

        if (organRepository.findByOrganName(request.getOrganName()).isPresent()) {
            throw OrganAlreadyExistException.EXCEPTION;
        }

        Organ organ = Organ.builder()
                .organName(request.getOrganName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        organRepository.save(organ);
    }
}
