package com.example.gujeuck_server.domain.log.entity;

import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import com.example.gujeuck_server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purpose_id", nullable = false)
    private Purpose purpose;

    private String name;

    private int age;

    private String phone;

    private boolean privacyAgreed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User companion;

    @Column(name = "male_count", nullable = false)
    private int maleCount;

    @Column(name = "female_count", nullable = false)
    private int femaleCount;

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    @PrePersist
    public void prePersist() {
        if(visitDate == null) {
            visitDate = LocalDate.now();
        }
    }
}

