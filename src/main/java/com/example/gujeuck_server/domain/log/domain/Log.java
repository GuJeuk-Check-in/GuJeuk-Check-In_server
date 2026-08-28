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

    /*
     * 운영 DB는 varchar(30)이다. 선언이 10으로 남아 있어 새로 만든 DB만
     * varchar(10)이 되고, 실제로 존재하는 12자 이름을 넣지 못했다.
     * 운영과 같은 값으로 맞춘다.
     */
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

    // 중복 체크인 판단 전용(초 단위까지 포함). 화면/엑셀 표시는 여전히 visitDate/visitTime(분 단위)을 사용한다.
    private LocalDateTime visitAt;

    // HA(고가용성) 경로에서만 클라이언트가 생성해 전달하는 요청 식별자. 재요청 시 중복검사 없이 멱등 처리하는 데 사용한다.
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
