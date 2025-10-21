package com.example.gujeuck_server.domain.user.repository;

import com.example.gujeuck_server.domain.user.entity.User;
import com.example.gujeuck_server.domain.user.entity.enums.Residence;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

    Boolean existsByUserId(String userId);

    List<User> findByResidence(String residence);

    List<User> findByResidenceNotIn (List<String> residences);

    Slice<User> findAllBy(Pageable pageable);

    long countByResidence(String residence);

    long countByResidenceNotIn(List<String> residences);
}
