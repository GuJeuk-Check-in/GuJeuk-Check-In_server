package com.example.gujeuck_server.domain.user.repository;

import com.example.gujeuck_server.domain.user.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<User> findByUserId(String userId);
}
