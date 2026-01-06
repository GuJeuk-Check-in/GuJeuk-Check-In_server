package com.example.gujeuck_server.domain.purpose.domain.repository;

import com.example.gujeuck_server.domain.purpose.domain.Purpose;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurposeRepository extends JpaRepository<Purpose, Long> {
    List<Purpose> findByPurpose(String purpose);
}
