package com.example.gujeuck_server.domain.common.funnel.domain;

import com.example.gujeuck_server.domain.common.funnel.domain.enums.VisitCountBucket;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.global.entity.BaseIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "check_in_funnel_event",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_check_in_funnel_event_client_event_id",
                        columnNames = "client_event_id"
                )
        }
)
public class CheckInFunnelEvent extends BaseIdEntity {

    @Column(name = "client_event_id", nullable = false, length = 36)
    private String clientEventId;

    @Column(name = "session_id", nullable = false, length = 36)
    private String sessionId;

    @Column(name = "event_name", nullable = false, length = 60)
    private String eventName;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @Column(name = "elapsed_ms_from_start", nullable = false)
    private long elapsedMsFromStart;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "age_group", length = 30)
    private Age ageGroup;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "failure_reason")
    private String failureReason;

    @Column(name = "is_existing_user")
    private Boolean isExistingUser;

    @Column(name = "visit_count")
    private Long visitCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "visit_count_bucket", length = 30)
    private VisitCountBucket visitCountBucket;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
