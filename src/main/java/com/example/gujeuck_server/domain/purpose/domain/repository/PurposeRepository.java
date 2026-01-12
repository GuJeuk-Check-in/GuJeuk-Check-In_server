package com.example.gujeuck_server.domain.purpose.domain.repository;

import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurposeRepository extends JpaRepository<Purpose, Long> {

    Optional<Purpose> findByPurpose(String purpose);

    List<Purpose> findAllByOrderByPurposeIndexAsc();

    List<Purpose> findAllByPurposeIndexGreaterThan(int purposeIndex);
}
