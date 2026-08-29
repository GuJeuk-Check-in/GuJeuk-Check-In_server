package com.example.gujeuck_server.domain.log.domain;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.global.entity.BaseIdEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "log",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_log_user_visit",
                        columnNames = {"user_id", "visit_at", "purpose"}
                ),
                @UniqueConstraint(
                        name = "uk_log_organ_name_age_purpose_visit",
                        columnNames = {"organ_id", "name", "age", "purpose", "visit_date", "visit_time"}
                ),
                @UniqueConstraint(
                        name = "uk_log_client_record_id",
                        columnNames = {"client_record_id"}
                )
        }
)
public class Log extends BaseIdEntity {

    @Column(nullable = false, length = 30)
    private String purpose;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 6)
    private Age age;

    @Column(nullable = false)
    private boolean privacyAgreed;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 15)
    private String phone;

    @Column(nullable = false)
    private int maleCount;

    @Column(nullable = false)
    private int femaleCount;

    @Column(nullable = false, length = 11)
    private String visitDate;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false, length = 5)
    private String visitTime;

    private LocalDateTime visitAt;

    private String clientRecordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  nullable = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organ_id", nullable = false)
    private Organ organ;

    public void updateLog(String name, Age age, String phone, int maleCount, int femaleCount, String purpose, String visitDate, String visitTime, LocalDateTime visitAt, boolean privacyAgreed) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
        this.purpose = purpose;
        this.visitDate = visitDate;
        this.visitTime = visitTime;
        this.visitAt = visitAt;
        this.privacyAgreed = privacyAgreed;
    }
}
