package com.example.gujeuck_server.domain.residence.domain.repository;

import com.example.gujeuck_server.domain.residence.domain.Residence;
import com.example.gujeuck_server.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResidenceRepository extends JpaRepository<Residence, Long>, ResidenceRepositoryCustom {
    Optional<Residence> findByOrganIdAndResidenceName(Long organId, String residenceName);

    List<Residence> findAllByOrganIdOrderByResidenceIndexAsc(Long organId);

    List<Residence> findAllByOrganIdAndResidenceIndexGreaterThan(Long organId, int residenceIndex);

    List<String> findAllResidenceNamesByOrganId(Long organId);

    Optional<Residence> findByResidenceNameAndOrganId(String residenceName, Long organId);
}
