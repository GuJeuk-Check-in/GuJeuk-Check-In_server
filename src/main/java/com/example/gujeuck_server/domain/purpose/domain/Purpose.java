package com.example.gujeuck_server.domain.purpose.domain;

import com.example.gujeuck_server.domain.organ.domain.Organ;
import com.example.gujeuck_server.global.entity.BaseIdEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Purpose extends BaseIdEntity {

    @Column(name = "purpose", nullable = false, length = 30)
    private String purposeName;

    @Column(nullable = false)
    private int purposeIndex;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organ_id", nullable = false)
    private Organ organ;

    public void updatePurpose(String purposeName) {
        this.purposeName = purposeName;
    }
}
