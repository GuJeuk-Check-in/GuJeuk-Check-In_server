package com.example.gujeuck_server.domain.purpose.domain.repository;

import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PurposeRepository extends JpaRepository<Purpose, Long>, PurposeRepositoryCustom {

    Optional<Purpose> findByPurposeName(String purposeName);

    List<Purpose> findAllByAdminIdOrderByPurposeIndexAsc(Long adminId);

    List<Purpose> findAllByPurposeIndexGreaterThan(int purposeIndex);
}
