package com.example.gujeuck_server.domain.log.entity;

import com.example.gujeuck_server.domain.purpose.entity.Purpose;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String age;
    private String phone;

    @Column(name = "male_count", nullable = false)
    private int maleCount;

    @Column(name = "female_count", nullable = false)
    private int femaleCount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "purpose_id", nullable = false)
    private Purpose purpose;

    @Column(name = "visit_date", nullable = false)
    private LocalDate visitDate;

    private boolean privacyAgreed;

    @PrePersist
    public void prePersist() {
        if(visitDate == null) {
            visitDate = LocalDate.now();
        }
    }
}

