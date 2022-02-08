package com.thinkmore.forum.service;

import com.thinkmore.forum.entity.JwtUser;
import com.thinkmore.forum.configuration.Config;
import com.thinkmore.forum.dto.users.UsersGetDto;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.mapper.UsersMapper;
import com.thinkmore.forum.repository.RolesRepository;
import com.thinkmore.forum.repository.UsersRepository;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final UsersMapper usersMapper;

    public Users getUserByUsername(String username) {
        return usersRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username %s not found", username)));
    }

    //only for jwt
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new JwtUser(getUserByUsername(username));
    }

    public UsersGetDto registration(String email, String username, String password) {
        Users user = new Users();

        user.setUsername(username);
        user.setPassword(Util.passwordEncoder(password));
        user.setEmail(email);
        user.setProfileImg(null);
        user.setRole(rolesRepository.findByRoleName(Config.DefaultRole).orElseThrow());
        user.setLastLoginTimestamp(OffsetDateTime.now());
        user.setCreateTimestamp(OffsetDateTime.now());

        usersRepository.save(user);

        return usersMapper.fromEntity(user);
    }

    public void updateLastLoginTimestamp(String username) {
        Users user = getUserByUsername(username);
        user.setLastLoginTimestamp(OffsetDateTime.now());
        usersRepository.save(user);
    }

    public boolean uniqueEmail(String email) {
        Optional<Users> find = usersRepository.findByEmail(email);

        boolean result = find.isEmpty();
        if (result){
            System.out.println("You can use this Email!");
        } else{
            System.out.println("Email is Already Registered. Please use another one.");
        }
        return find.isEmpty();
    }

    public boolean uniqueUsername(String username) {
        Optional<Users> find = usersRepository.findByUsername(username);

        boolean result = find.isEmpty();
        if (result){
            System.out.println("This is a validate username.");
        } else{
            System.out.println("Username has already been taken.Please use another one.");
        }
        return find.isEmpty();
    }


}
