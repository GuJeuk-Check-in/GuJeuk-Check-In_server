package com.example.gujeuck_server.domain.organ.domain.repository;

import com.example.gujeuck_server.domain.organ.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
