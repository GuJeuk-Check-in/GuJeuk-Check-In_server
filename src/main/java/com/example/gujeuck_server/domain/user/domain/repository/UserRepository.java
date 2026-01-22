package com.example.gujeuck_server.domain.user.domain.repository;

import com.example.gujeuck_server.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Boolean existsByUserId(String userId);

    Slice<User> findByResidenceAndOrganId(String residence, Long organId, Pageable pageable);

    Slice<User> findByOrganIdAndResidenceNotIn (Long organId, List<String> residences, Pageable pageable);

    Slice<User> findAllByOrganId(Pageable pageable, Long organId);

    long countByResidenceAndOrganId(String residence, Long organId);

    long countByOrganIdAndResidenceNotIn(Long organId, List<String> residences);

    Optional<User> findByUserId(String userId);
}
