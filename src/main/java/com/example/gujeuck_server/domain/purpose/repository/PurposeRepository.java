package com.example.gujeuck_server.domain.purpose.repository;

import com.example.gujeuck_server.domain.purpose.dto.request.PurposeMoveRequest;
import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurposeRepository extends JpaRepository<Purpose, Long> {
    List<Purpose> findByPurpose(String purpose);
    List<Purpose> findAllByOrderByPurposeIndexAsc();
}
