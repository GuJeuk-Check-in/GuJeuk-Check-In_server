package com.example.gujeuck_server.domain.repository;

import com.example.gujeuck_server.domain.entity.PurposeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurposeRepository extends JpaRepository<PurposeEntity, Long> {

}
