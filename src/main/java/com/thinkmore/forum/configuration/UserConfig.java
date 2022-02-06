package com.thinkmore.forum.configuration;

import com.thinkmore.forum.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@RequiredArgsConstructor
public class UserConfig implements UserDetails {

    private final String username;
    private final String password;
    //    private final Set<? extends GrantedAuthority> grantedAuthorities;
    private final boolean isAccountNotExpired;
    private final boolean isAccountNonLocked;
    private final boolean isCredentialsNonExpired;
    private final boolean isEnabled;

    public UserConfig(Users user) {
        username = user.getUsername();
        password = user.getPassword();
        isAccountNotExpired = true;
        isAccountNonLocked = true;
        isCredentialsNonExpired = true;
        isEnabled = true;
    }

    @Override
    public Set<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
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
        return isAccountNotExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
