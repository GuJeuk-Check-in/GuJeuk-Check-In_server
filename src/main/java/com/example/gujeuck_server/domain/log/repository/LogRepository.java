package com.example.gujeuck_server.domain.log.repository;

import com.example.gujeuck_server.domain.log.entity.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
    Slice<Log> findAllBy(Pageable pageable);
}
