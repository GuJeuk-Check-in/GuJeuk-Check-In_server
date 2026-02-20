package com.example.gujeuck_server.domain.user.domain.repository;

import com.example.gujeuck_server.domain.user.domain.User;

import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<User> findByUserIdAndOrganId(String userId, Long organId);
}
