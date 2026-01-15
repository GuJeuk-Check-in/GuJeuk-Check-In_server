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

  Slice<Log> findAllBy(Pageable pageable);

  List<Log> findAllByVisitDateStartingWith(String visitDatePrefix);

  @Query("""
        SELECT l
        FROM Log l
        WHERE l.visitDate LIKE CONCAT(:year, '년', :month, '월%')
    """)
  Slice<QueryLogListResponse> findByYearAndMonth(
          @Param("year") int year,
          @Param("month") int month,
          Pageable pageable
  );

  @Query("""
    SELECT COUNT(l)
    FROM Log l
    WHERE l.visitDate LIKE CONCAT(:year, '년', :month, '월%')
""")
  long countByYearAndMonth(
          @Param("year") int year,
          @Param("month") int month
  );
}
