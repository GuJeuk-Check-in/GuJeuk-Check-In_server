package com.example.gujeuck_server.domain.repository;

import com.example.gujeuck_server.domain.dto.PurposeResponse;
import com.example.gujeuck_server.domain.entity.Purpose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurposeRepository extends JpaRepository<Purpose, Long> {
    List<PurposeResponse> findAllPurpose();
}
