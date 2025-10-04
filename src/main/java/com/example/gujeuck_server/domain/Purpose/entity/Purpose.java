package com.example.gujeuck_server.domain.entity;

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

    @Column(name = "purpose_image")
    private String purposeImage;

    public void updatePurpose(String purpose, String purposeImage) {
        this.purpose = purpose;
        this.purposeImage = purposeImage;
    }
}
