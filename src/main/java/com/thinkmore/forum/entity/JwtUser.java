package com.thinkmore.forum.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class JwtUser implements UserDetails {

    private final String username;
    private final String password;

    @Getter
    private final UUID id;
    @Getter
    private final String roleName;
    @Getter
    private final String permission;

    public JwtUser(Users user) {
        username = user.getUsername();
        password = user.getPassword();

        id = user.getId();
        roleName = user.getRole().getRoleName();
        permission = user.getRole().getPermission();
    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
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
