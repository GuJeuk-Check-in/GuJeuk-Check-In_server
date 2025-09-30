package com.example.gujeuck_server.domain.repository;

import com.example.gujeuck_server.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
