package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.users.*;
import com.thinkmore.forum.entity.JwtUser;
import com.thinkmore.forum.configuration.StaticConfig;
import com.thinkmore.forum.entity.Roles;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.exception.InvalidOldPasswordException;
import com.thinkmore.forum.exception.UserNotFoundException;
import com.thinkmore.forum.mapper.UsersMapper;
import com.thinkmore.forum.repository.RolesRepository;
import com.thinkmore.forum.repository.UsersRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;
    private final RolesRepository rolesRepository;

    //only for jwt
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Login Email %s not found", email)));
        return new JwtUser(user);
    }

    @Transactional
    public Boolean register(UsersPostDto usersPostDto) {
        Users user = usersMapper.toEntity(usersPostDto);

        user.setUsername(usersPostDto.getUsername());
        user.setPassword(passwordEncoder.encode(usersPostDto.getPassword()));
        user.setEmail(usersPostDto.getEmail());
        user.setRole(rolesRepository.findByRoleName(StaticConfig.DefaultRole).orElseThrow());

        usersRepository.save(user);

        return true;
    }

    public UsersGetDto updateLastLoginTimestamp(String username) {
        Users user = usersRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username %s not found", username)));
        user.setLastLoginTimestamp(OffsetDateTime.now());
        usersRepository.save(user);
        return usersMapper.fromEntity(user);
    }

    public boolean uniqueEmail(String email) {
        return usersRepository.findByEmail(email).isEmpty();
    }

    public boolean uniqueUsername(String username) {
        return usersRepository.findByUsername(username).isEmpty();
    }

    public boolean changeUsername(UUID usersId, String newUsername) {
        Users user = usersRepository.findById(usersId)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));

        user.setUsername(newUsername);
        usersRepository.save(user);
        return true;
    }

    @Transactional
    public boolean changeHeadImgUrl(UUID usersId, UsersImgPutDto usersImgPutDto) {
        Users user = usersRepository.findById(usersId)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));

        user.setHeadImgUrl(usersImgPutDto.getHeadImgUrl());
        usersRepository.save(user);
        return true;
    }

    @Transactional
    public boolean changeProfileImgUrl(UUID usersId, UsersImgPutDto usersImgPutDto) {
        Users user = usersRepository.findById(usersId)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));

        user.setProfileImgUrl(usersImgPutDto.getProfileImgUrl());
        usersRepository.save(user);
        return true;
    }

    public boolean changeEmail(UUID usersId, String newEmail) {
        Users user = usersRepository.findById(usersId)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));

        user.setEmail(newEmail);
        usersRepository.save(user);

        return true;
    }

    public boolean changePassword(UUID usersId, UsersMiniPutDto usersMiniPutDto) {
        Users user = usersRepository.findById(usersId)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));

        if (!passwordEncoder.matches(usersMiniPutDto.getOldPassword(), user.getPassword())) {
            throw new InvalidOldPasswordException("Old password is wrong");
        }

        user.setPassword(passwordEncoder.encode(usersMiniPutDto.getNewPassword()));
        usersRepository.save(user);
        return true;
    }

    public boolean resetPassword(UUID usersId, UsersPasswordPutDto password) {
        Users user = usersRepository.findById(usersId)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));

        user.setPassword(passwordEncoder.encode(password.getPassword()));
        usersRepository.save(user);
        return true;
    }

    public UsersGetDto getUserByUsername(String username) {
        Optional<Users> targetUsers = usersRepository.findByUsername(username);
        return targetUsers.map(usersMapper::fromEntity).orElse(null);
    }

    public List<UsersGetDto> getUserByContainingString(String string) {
        List<Users> users = usersRepository.findByUsernameContainingIgnoreCase(string);
        List<UsersGetDto> usersGetDto = new ArrayList<>();
        for (Users user : users) {
            usersGetDto.add(usersMapper.fromEntity(user));
        }
        return usersGetDto;
    }

    public List<UsersGetDto> getAllUsers() {
        return usersRepository.findAll().stream()
                .map(usersMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public void changeSingleUserRole(UsersGetDto inputUserGetDto) {
        Users userToUpdate = usersRepository.findById(inputUserGetDto.getId()).get();
        Roles newRoleOfUser = rolesRepository.findByRoleName(inputUserGetDto.getRole().getRoleName()).orElseThrow();
        userToUpdate.setRole(newRoleOfUser);
        usersRepository.save(userToUpdate);
    }

    public void changeUsersRoles(List<UsersGetDto> usersGetDtoList) {
        usersGetDtoList.forEach(singleUserGetDto -> changeSingleUserRole(singleUserGetDto));
    }
}
