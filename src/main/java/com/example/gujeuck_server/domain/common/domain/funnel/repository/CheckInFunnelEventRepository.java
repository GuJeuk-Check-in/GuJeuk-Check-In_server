package com.example.gujeuck_server.domain.common.domain.funnel.repository;

import com.example.gujeuck_server.domain.common.domain.funnel.CheckInFunnelEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

public interface CheckInFunnelEventRepository extends JpaRepository<CheckInFunnelEvent, Long> {
    @Modifying
    @Query(value = """
            INSERT INTO check_in_funnel_event (
                client_event_id,
                session_id,
                event_name,
                occurred_at,
                elapsed_ms_from_start,
                user_id,
                age_group,
                purpose,
                failure_reason,
                is_existing_user,
                visit_count,
                visit_count_bucket
            )
            VALUES (
                :clientEventId,
                :sessionId,
                :eventName,
                :occurredAt,
                :elapsedMsFromStart,
                :userId,
                :ageGroup,
                :purpose,
                :failureReason,
                :isExistingUser,
                :visitCount,
                :visitCountBucket
            )
            ON DUPLICATE KEY UPDATE client_event_id = client_event_id
            """, nativeQuery = true)
    void insertKeepingExistingClientEvent(
            @Param("clientEventId") String clientEventId,
            @Param("sessionId") String sessionId,
            @Param("eventName") String eventName,
            @Param("occurredAt") Instant occurredAt,
            @Param("elapsedMsFromStart") Long elapsedMsFromStart,
            @Param("userId") Long userId,
            @Param("ageGroup") String ageGroup,
            @Param("purpose") String purpose,
            @Param("failureReason") String failureReason,
            @Param("isExistingUser") Boolean isExistingUser,
            @Param("visitCount") Long visitCount,
            @Param("visitCountBucket") String visitCountBucket
    );
}
