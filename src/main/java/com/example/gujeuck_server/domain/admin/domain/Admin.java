package com.example.gujeuck_server.domain.admin.domain;

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
public class Admin extends BaseIdEntity {

    @Column(nullable = false, length = 100)
    private String password;

    public void changePassword(String password) {
        this.password = password;
    }
}
