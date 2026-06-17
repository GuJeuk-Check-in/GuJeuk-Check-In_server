package com.example.gujeuck_server.domain.pet.domain.repository;

import com.example.gujeuck_server.domain.pet.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import jakarta.persistence.LockModeType;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    boolean existsByPetUserId(Long petUserId);

    Optional<Pet> findByPetUserId(Long petUserId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Pet> findWithLockByPetUserId(Long petUserId);
}
