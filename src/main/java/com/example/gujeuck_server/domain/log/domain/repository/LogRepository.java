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

  /**
   * 같은 방문이 이미 있는지.
   *
   * 판정은 visitAt(초 단위)으로 한다. 예전에는 visitDate·visitTime 문자열을
   * 비교했는데 visitTime 이 분 단위("HH:mm")라, 같은 분에 초만 다른 방문이
   * 중복으로 막혔다. 실제로 운영에 38초·55초로 나뉜 기록이 있다.
   *
   * 수정할 때는 자기 자신이 걸리므로 제외해야 한다. 그렇지 않으면 다른 필드만
   * 고쳐도 중복으로 잡혀 아무것도 저장할 수 없다. 생성할 때는 제외할 대상이
   * 없으므로 excludedId 에 null 을 넘긴다.
   *
   * 파생 쿼리(...AndIdNot)로는 이 둘을 한 메서드로 묶을 수 없다. null 을 넘기면
   * `id <> null` 이 되어 어떤 행도 매치되지 않고, 생성 시 중복 검사가 조용히
   * 무력화된다. 그래서 JPQL 로 직접 쓴다.
   */
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
