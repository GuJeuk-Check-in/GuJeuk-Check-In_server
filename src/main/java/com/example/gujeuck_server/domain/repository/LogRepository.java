package com.example.gujeuck_server.domain.repository;

import com.example.gujeuck_server.domain.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {

}
