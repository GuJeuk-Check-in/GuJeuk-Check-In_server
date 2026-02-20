package com.example.gujeuck_server.domain.residence.domain;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.global.entity.BaseIdEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Residence extends BaseIdEntity {
    @Column(name = "residence", nullable = false, length = 30)
    private String residenceName;

    @Column(nullable = false)
    private int residenceIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organ_id", nullable = false)
    private Organ organ;

    public void updateResidence(String residenceName) {
        this.residenceName = residenceName;
    }
}
