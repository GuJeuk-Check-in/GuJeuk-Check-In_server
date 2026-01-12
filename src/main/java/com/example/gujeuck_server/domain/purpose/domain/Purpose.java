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

    @Column(nullable = false, length = 30)
    private String purpose;

    private int purposeIndex;

    public void updatePurpose(String purpose) {
        this.purpose = purpose;
    }
}
