package com.example.gujeuck_server.domain.purpose.domain;

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

    private int purposeIndex;

    public void updatePurpose(String purposeName) {
        this.purposeName = purposeName;
    }
}
