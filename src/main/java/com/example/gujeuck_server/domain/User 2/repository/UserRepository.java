package com.example.gujeuck_server.domain.User.repository;

import com.example.gujeuck_server.domain.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(String userId);

    Boolean existsByUserId(String userId);

}
