package com.example.gujeuck_server.domain.log.entity;

import com.example.gujeuck_server.domain.user.entity.User;
import com.example.gujeuck_server.domain.user.entity.enums.Age;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

public class Log {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String purpose;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Age age;

    private boolean privacyAgreed;

    @Column(nullable = false, length = 30)
    private String name;
  
    @Column(nullable = false, length = 20)
    private String phone;

    private int maleCount;

    private int femaleCount;

    private String visitDate;

    @Column(nullable = false)
    private int year;

    @Column(length = 30)
    private String residence;

    private String visitTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


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

