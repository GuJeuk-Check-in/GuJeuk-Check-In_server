package com.example.gujeuck_server.domain.log.domain.repository;

import com.example.gujeuck_server.domain.log.domain.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long>, LogRepositoryCustom {

  Slice<Log> findAllByOrganId(Pageable pageable, Long organId);

  List<Log> findAllByOrganIdAndVisitDateStartingWith(Long organId, String visitDate);

  Slice<Log> findAllByOrganIdAndVisitDateStartingWith(Long organId, String visitDate, Pageable pageable);
}
