package com.example.burserker.gujeuk.domain.organ.domain.repository;

import com.example.burserker.gujeuk.domain.organ.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    Optional<RefreshToken> findByOrganName(String organName);
}
