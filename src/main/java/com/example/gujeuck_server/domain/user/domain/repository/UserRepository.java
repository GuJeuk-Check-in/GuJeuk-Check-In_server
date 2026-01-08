package com.example.gujeuck_server.domain.user.domain.repository;

import com.example.gujeuck_server.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Boolean existsByUserId(String userId);

    List<User> findByResidence(String residence);

    List<User> findByResidenceNotIn (List<String> residences);

    Slice<User> findAllBy(Pageable pageable);

    long countByResidence(String residence);

    long countByResidenceNotIn(List<String> residences);
}
