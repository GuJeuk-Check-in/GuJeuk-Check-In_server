package com.example.gujeuck_server.domain.log.domain.repository;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.log.presentation.dto.response.MonthlyLogCountResponse;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Long>, LogRepositoryCustom {

  Slice<Log> findAllByOrganId(Pageable pageable, Long organId);

  Slice<Log> findAllByOrganIdAndVisitDateStartingWith(Long organId, String visitDate, Pageable pageable);

  Optional<Log> findByIdAndOrganId(Long id, Long organId);

  Optional<Log> findByClientRecordId(String clientRecordId);

  Optional<Log> findFirstByNameAndUserIsNotNullOrderByIdDesc(String name);

  long countByUserId(Long userId);

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

  @Query("""
    SELECT new com.example.gujeuck_server.domain.log.presentation.dto.response.MonthlyLogCountResponse(
        CAST(SUBSTRING(l.visitDate, 6, 2) AS Integer), COUNT(l)
    )
    FROM Log l
    WHERE l.visitDate LIKE CONCAT(:year, '년%')
    GROUP BY SUBSTRING(l.visitDate, 6, 2)
    ORDER BY SUBSTRING(l.visitDate, 6, 2)
""")
  List<MonthlyLogCountResponse> findMonthlyCounts(@Param("year") String year);
}
