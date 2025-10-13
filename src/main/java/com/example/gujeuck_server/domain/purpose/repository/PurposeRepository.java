package com.example.gujeuck_server.domain.purpose.repository;

import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurposeRepository extends JpaRepository<Purpose, Long> {
    Optional<Purpose> findByPurpose(String purpose);
}
