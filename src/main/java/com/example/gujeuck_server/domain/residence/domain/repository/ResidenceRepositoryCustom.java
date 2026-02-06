package com.example.gujeuck_server.domain.residence.domain.repository;

import com.example.gujeuck_server.domain.user.domain.User;

import java.util.List;

public interface ResidenceRepositoryCustom {
    int findMaxResidenceIndexByOrganId(Long organId);
}
