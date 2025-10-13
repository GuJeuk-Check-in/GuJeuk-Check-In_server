package com.example.gujeuck_server.domain.log.entity;

import com.example.gujeuck_server.domain.user.entity.User;
import com.example.gujeuck_server.domain.user.entity.enums.Age;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
  
    @Column(nullable = false, length = 11)
    private String phone;

    @Column(name = "male_count")
    private int maleCount;

    @Column(name = "female_count")
    private int femaleCount;

    @Column(name = "visit_date")
    private String visitDate;
}

