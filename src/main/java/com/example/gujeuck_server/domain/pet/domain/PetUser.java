package com.example.gujeuck_server.domain.pet.domain;

import com.example.gujeuck_server.global.entity.BaseIdEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetUser extends BaseIdEntity {

    @Column(nullable = false, length = 30)
    private String name;

    private String phone;

    public void syncProfile(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
