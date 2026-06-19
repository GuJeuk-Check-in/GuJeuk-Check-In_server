package com.example.gujeuck_server.domain.log.domain.repository;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Long>, LogRepositoryCustom {

  Slice<Log> findAllByOrganId(Pageable pageable, Long organId);

  List<Log> findAllByOrganIdAndVisitDateStartingWith(Long organId, String visitDate);

  Slice<Log> findAllByOrganIdAndVisitDateStartingWith(Long organId, String visitDate, Pageable pageable);

  Optional<Log> findByIdAndOrganId(Long id, Long organId);

  boolean existsByOrganIdAndNameAndAgeAndPurposeAndVisitDateAndVisitTime(
          Long organId,
          String name,
          Age age,
          String purpose,
          String visitDate,
          String visitTime
  );

  boolean existsByOrganIdAndNameAndAgeAndPurposeAndVisitDateAndVisitTimeAndIdNot(
          Long organId,
          String name,
          Age age,
          String purpose,
          String visitDate,
          String visitTime,
          Long id
  );
}
