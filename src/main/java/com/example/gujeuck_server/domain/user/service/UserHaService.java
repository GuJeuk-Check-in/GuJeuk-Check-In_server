package com.example.gujeuck_server.domain.user.service;

import com.example.gujeuck_server.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserHaService {
    private final UserRepository userRepository;

    @Transactional
    public void execute()
}
