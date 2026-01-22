package com.example.gujeuck_server.domain.organ.domain.repository;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganRepository extends JpaRepository<Organ, Long> {

    Optional<Organ> findByPassword(String password);

}
