package com.example.gujeuck_server.global.security.auth;

import com.example.gujeuck_server.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public record CustomUserDetails(User user) implements UserDetails {
    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {    //사용자의 권한 목록을 반환
        return new ArrayList<>(Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getName())));
    }

    @Override
    public String getPassword() {
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
