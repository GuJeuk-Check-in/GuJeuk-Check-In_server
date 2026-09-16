package com.example.gujeuck_server.domain.user.domain.repository;

import com.example.gujeuck_server.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Slice<User> findByResidenceAndOrganId(String residence, Long organId, Pageable pageable);

    Slice<User> findByOrganIdAndResidenceNotIn (Long organId, List<String> residences, Pageable pageable);

    Slice<User> findAllByOrganId(Pageable pageable, Long organId);

    long countByResidenceAndOrganId(String residence, Long organId);

    long countByOrganId(Long organId);

    long countByOrganIdAndResidenceNotIn(Long organId, List<String> residences);

    boolean existsByIdAndOrganId(Long id, Long organId);

    List<User> findAllByName(String name);

    List<User> findAllByOrganIdOrderByIdAsc(Long organId);
}
