package com.example.gujeuck_server.domain.user.domain.repository;

import com.example.gujeuck_server.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Boolean existsByUserId(String userId);

    Slice<User> findByResidence(String residence, Pageable pageable);

    Slice<User> findByResidenceNotIn (List<String> residences, Pageable pageable);

    Slice<User> findAllByAdminId(Pageable pageable, Long adminId);

    long countByResidenceAndAdminId(String residence, Long adminId);

    long countByResidenceNotIn(List<String> residences);
}
