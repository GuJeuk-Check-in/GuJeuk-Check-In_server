package com.example.gujeuck_server.domain.user.domain.repository;

import com.example.gujeuck_server.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Slice<User> findByResidenceAndOrganId(String residence, Long organId, Pageable pageable);

    Slice<User> findByOrganIdAndResidenceNotIn (Long organId, List<String> residences, Pageable pageable);

    Slice<User> findAllByOrganId(Pageable pageable, Long organId);

    long countByResidenceAndOrganId(String residence, Long organId);

    long countByOrganId(Long organId);

    long countByOrganIdAndResidenceNotIn(Long organId, List<String> residences);

    Optional<User> findByNameAndPhone(String name, String phone);

    @Query("select u from User u where replace(replace(u.phone, '-', ''), ' ', '') = :phone")
    List<User> findAllByNormalizedPhone(@Param("phone") String phone);

    List<User> findAllByOrganIdOrderByIdAsc(Long organId);
}
