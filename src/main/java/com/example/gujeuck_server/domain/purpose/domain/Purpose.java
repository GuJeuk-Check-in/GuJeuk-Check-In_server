package com.example.gujeuck_server.domain.purpose.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Purpose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "purpose", nullable = false, length = 30)
    private String purposeName;

    private int purposeIndex;

    public void updatePurpose(String purposeName) {
        this.purposeName = purposeName;
    }
}
