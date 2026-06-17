package com.example.gujeuck_server.domain.pet.domain.repository;

import com.example.gujeuck_server.domain.pet.domain.PetUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetUserRepository extends JpaRepository<PetUser, Long> {
    Optional<PetUser> findByPhone(String phone);
}
