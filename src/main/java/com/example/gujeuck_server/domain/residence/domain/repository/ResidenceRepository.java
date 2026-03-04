package com.example.gujeuck_server.domain.residence.domain.repository;

import com.example.gujeuck_server.domain.residence.domain.Residence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResidenceRepository extends JpaRepository<Residence, Long>, ResidenceRepositoryCustom {
    Optional<Residence> findByOrganIdAndResidenceName(Long organId, String residenceName);

    List<Residence> findAllByOrganIdOrderByResidenceIndexAsc(Long organId);

    List<Residence> findAllByOrganIdAndResidenceIndexGreaterThan(Long organId, int residenceIndex);

    @Query("select r.residenceName from Residence r where r.organ.id = :organId")
    List<String> findAllResidenceNameByOrganId(Long organId);

    Optional<Residence> findByResidenceNameAndOrganId(String residenceName, Long organId);

    List<Residence> findAllByOrderByResidenceIndexAsc();
}
