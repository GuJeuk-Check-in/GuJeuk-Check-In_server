package com.example.gujeuck_server.domain.pet.domain.repository;

import com.example.gujeuck_server.domain.pet.domain.PetUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PetUserRepository extends JpaRepository<PetUser, Long> {
    Optional<PetUser> findByPhone(String phone);

    @Query("select p from PetUser p where replace(replace(p.phone, '-', ''), ' ', '') = :phone")
    Optional<PetUser> findByNormalizedPhone(@Param("phone") String phone);
}
