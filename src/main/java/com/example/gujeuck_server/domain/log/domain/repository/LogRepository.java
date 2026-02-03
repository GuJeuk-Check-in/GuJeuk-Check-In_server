package com.example.gujeuck_server.domain.log.domain.repository;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.presentation.dto.response.QueryLogListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long>, LogRepositoryCustom {

  Slice<Log> findAllByOrganId(Pageable pageable, Long organId);

  List<Log> findAllByOrganIdAndVisitDateStartingWith(Long organId, String visitDatePrefix);
}
