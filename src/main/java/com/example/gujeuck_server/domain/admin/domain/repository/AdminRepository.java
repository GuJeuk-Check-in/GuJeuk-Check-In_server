package com.example.gujeuck_server.domain.admin.domain.repository;

import com.example.gujeuck_server.domain.admin.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByPassword(String password);

}
