package com.thinkmore.forum.service;

import com.thinkmore.forum.configuration.UserConfig;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users u = usersRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username %s not found", username)));

        return new UserConfig(u);
    }
}
