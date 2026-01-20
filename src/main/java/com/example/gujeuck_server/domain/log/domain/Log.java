package com.example.gujeuck_server.domain.log.domain;

import com.example.gujeuck_server.domain.admin.domain.Admin;
import com.example.gujeuck_server.domain.user.domain.User;
import com.example.gujeuck_server.domain.user.domain.enums.Age;
import com.example.gujeuck_server.global.entity.BaseIdEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "log",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_log_user_visit",
                        columnNames = {"user_id", "visit_date", "visit_time"}
                )
        }
)
public class Log extends BaseIdEntity {

    @Column(nullable = false)
    private String purpose;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Age age;

    @Column(nullable = false)
    private boolean privacyAgreed;

    @Column(nullable = false, length = 30)
    private String name;
  
    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false)
    private int maleCount;

    @Column(nullable = false)
    private int femaleCount;

    @Column(nullable = false)
    private String visitDate;

    @Column(nullable = false)
    private int year;

    @Column(length = 30)
    private String residence;

    @Column(nullable = false)
    private String visitTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  nullable = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    public void updateLog(String name, Age age, String phone, int maleCount, int femaleCount, String purpose, String visitDate, boolean privacyAgreed) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
        this.purpose = purpose;
        this.visitDate = visitDate;
        this.privacyAgreed = privacyAgreed;
    }
}

