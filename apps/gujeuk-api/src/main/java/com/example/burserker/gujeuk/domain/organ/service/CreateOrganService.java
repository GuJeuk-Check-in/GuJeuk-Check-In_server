package com.example.burserker.gujeuk.domain.organ.service;

import com.example.burserker.gujeuk.domain.organ.exception.OrganAlreadyExistException;
import com.example.burserker.gujeuk.domain.organ.presentation.dto.request.CreateOrganRequest;
import com.example.burserker.gujeuk.domain.organ.domain.Organ;
import com.example.burserker.gujeuk.domain.organ.domain.repository.OrganRepository;
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
    public void execute(CreateOrganRequest request) {
        if (organRepository.findByOrganName(request.organName()).isPresent()) {
            throw OrganAlreadyExistException.EXCEPTION;
        }

        Organ organ = Organ.builder()
                .organName(request.organName())
                .password(passwordEncoder.encode(request.password()))
                .build();

        organRepository.save(organ);
    }
}
