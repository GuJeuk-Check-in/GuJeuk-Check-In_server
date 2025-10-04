package com.example.gujeuck_server.domain.User.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 30, name = "user_id")
    private String userId;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 11)
    private String phone;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false, length = 6, name = "birth_ymd")
    private String birthYMD;

    @Column(nullable = false, length = 30)
    private String residence;

    @Column(nullable = false, name = "privacy_agreed")
    private boolean privacyAgreed;
}
