package com.example.gujeuck_server.domain.log.domain.repository;

import com.example.gujeuck_server.domain.log.domain.Log;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Long>, LogRepositoryCustom {

  Slice<Log> findAllByOrganId(Pageable pageable, Long organId);

  Slice<Log> findAllByOrganIdAndVisitDateStartingWith(Long organId, String visitDate, Pageable pageable);

  Optional<Log> findByIdAndOrganId(Long id, Long organId);

  Optional<Log> findByClientRecordId(String clientRecordId);

  Optional<Log> findFirstByNameAndUserIsNotNullOrderByIdDesc(String name);

  long countByUserId(Long userId);

  @Query("""
          select count(l) > 0 from Log l
          where l.organ.id = :organId
            and l.name = :name
            and l.age = :age
            and l.purpose = :purpose
            and l.visitAt = :visitAt
            and (:excludedId is null or l.id <> :excludedId)
          """)
  boolean existsDuplicate(
          @Param("organId") Long organId,
          @Param("name") String name,
          @Param("age") Age age,
          @Param("purpose") String purpose,
          @Param("visitAt") LocalDateTime visitAt,
          @Param("excludedId") Long excludedId
  );
}
