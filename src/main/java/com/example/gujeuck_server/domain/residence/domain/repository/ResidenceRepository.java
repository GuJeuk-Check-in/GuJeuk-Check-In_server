package com.example.gujeuck_server.domain.residence.domain.repository;

import com.example.gujeuck_server.domain.residence.domain.Residence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResidenceRepository extends JpaRepository<Residence, Long>, ResidenceRepositoryCustom {
    Optional<Residence> findByOrganIdAndResidenceName(Long organId, String residenceName);

}
