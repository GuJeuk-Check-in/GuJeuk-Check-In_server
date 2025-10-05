package com.example.gujeuck_server.domain.Log.repository;

import com.example.gujeuck_server.domain.Log.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {

}
