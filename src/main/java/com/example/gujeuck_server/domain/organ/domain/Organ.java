package com.example.gujeuck_server.domain.organ.domain;

import com.example.gujeuck_server.global.entity.BaseIdEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Organ extends BaseIdEntity {

    @Column(nullable = false)
    private String organName;

    @Column(nullable = false, length = 300)
    private String password;

    public void changePassword(String password) {
        this.password = password;
    }
}
