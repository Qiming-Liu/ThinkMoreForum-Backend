package com.thinkmore.forum.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class JwtUser implements UserDetails {

    private final String username;
    private final String password;

    @Getter
    @Setter
    private UUID id;
    @Getter
    @Setter
    private String roleName;
    @Getter
    @Setter
    private String permission;

    public JwtUser(Users user) {
        username = user.getUsername();
        password = user.getPassword();

        id = user.getId();
        roleName = user.getRole().getRoleName();
        permission = user.getRole().getPermission();
    }

    public JwtUser(ArrayList<String> principal) {
        username = null;
        password = null;

        id = UUID.fromString(principal.get(0));
        roleName = principal.get(1);
        permission = principal.get(2);
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
