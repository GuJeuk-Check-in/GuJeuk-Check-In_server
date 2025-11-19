package com.example.gujeuck_server.domain.log.repository;

import com.example.gujeuck_server.domain.log.dto.response.LogResponse;
import com.example.gujeuck_server.domain.log.entity.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long>, LogRepositoryCustom {
  Slice<Log> findAllBy(Pageable pageable);
  List<Log> findAllByVisitDateStartingWith(String visitDatePrefix);
}
