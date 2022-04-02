package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.oauth.OauthPostDto;
import com.thinkmore.forum.dto.users.*;
import com.thinkmore.forum.entity.JwtUser;
import com.thinkmore.forum.configuration.Config;
import com.thinkmore.forum.entity.Oauth;
import com.thinkmore.forum.entity.Roles;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.exception.InvalidOldPasswordException;
import com.thinkmore.forum.exception.UserNotFoundException;
import com.thinkmore.forum.mapper.UsersMapper;
import com.thinkmore.forum.message.ResetPasswordEmailMessage;
import com.thinkmore.forum.message.VerificationEmailMessage;
import com.thinkmore.forum.repository.OauthRepository;
import com.thinkmore.forum.repository.RolesRepository;
import com.thinkmore.forum.repository.UsersRepository;
import com.thinkmore.forum.configuration.Singleton;
import com.thinkmore.forum.util.Util;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Value("${domain.name}")
    public String domainName;

    @Autowired
    RabbitTemplate rabbitTemplate;

    private final UsersRepository usersRepository;
    private final OauthRepository oauthRepository;
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
        user.setPassword(Singleton.passwordEncoder().encode(usersPostDto.getPassword()));
        user.setEmail(usersPostDto.getEmail());
        user.setRole(rolesRepository.findByRoleName(Config.DefaultRole).orElseThrow());

        usersRepository.save(user);

        return true;
    }

    @Transactional
    public Boolean thirdPartyLogin(String email, String username, OauthPostDto oauthPostDto) {
        if (!uniqueEmail(email)) {
            Users users = usersRepository.findByEmail(email).get();
            users.setPassword(Singleton.passwordEncoder().encode(oauthPostDto.getOpenid()));

            Oauth oauth = new Oauth();

            oauth.setUsers(users);
            oauth.setOauthType(oauthPostDto.getOauthType());
            oauth.setOpenid(oauthPostDto.getOpenid());

            oauthRepository.save(oauth);
            return true;
        } else if (oauthRepository.findByOpenid(oauthPostDto.getOpenid()).isPresent()) {
            return true;
        }

        Users user = new Users();

        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(Singleton.passwordEncoder().encode(oauthPostDto.getOpenid()));
        user.setRole(rolesRepository.findByRoleName(Config.DefaultRole).orElseThrow());

        usersRepository.save(user);

        Oauth oauth = new Oauth();

        oauth.setUsers(user);
        oauth.setOauthType(oauthPostDto.getOauthType());
        oauth.setOpenid(oauthPostDto.getOpenid());

        oauthRepository.save(oauth);
        return true;
    }

    @Transactional
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

    public boolean hasOpenid(UUID usersId) {
        Users user = usersRepository.findById(usersId).get();
        return oauthRepository.findByUsers(user).isPresent();
    }

    @Transactional
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

    @Transactional
    public boolean sendVerificationEmail(UUID usersId, String newEmail) {
        VerificationEmailMessage message = new VerificationEmailMessage(usersId, newEmail);
        rabbitTemplate.convertAndSend("VerificationEmail", message);
        return true;
    }

    @Transactional
    @RabbitListener(queues = "VerificationEmail")
    public void handleVerificationEmail(VerificationEmailMessage message) throws Exception {
        Users user = usersRepository.findById(message.getUsersId())
                                    .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));

        Util.createMail(
                Config.fromEmail,
                user.getEmail(),
                "Change Email Request",
                "Your account " + user.getUsername() + " is changing email to " + message.getNew_email());

        Util.createMail(
                Config.fromEmail,
                message.getNew_email(),
                "Verify Email",
                Config.VerifyEmailContext + domainName + Config.VerifyEmailUrl + message.getNew_email());
    }

    @Transactional
    public boolean changeEmail(UUID usersId, String newEmail) {
        Users user = usersRepository.findById(usersId)
                                    .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));

        user.setEmail(newEmail);
        usersRepository.save(user);

        return true;
    }

    @Transactional
    public boolean changePassword(UUID usersId, UsersMiniPutDto usersMiniPutDto) {
        Users user = usersRepository.findById(usersId)
                                    .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));

        if (!Singleton.passwordEncoder().matches(usersMiniPutDto.getOldPassword(), user.getPassword())) {
            throw new InvalidOldPasswordException("Old password is wrong");
        }

        user.setPassword(Singleton.passwordEncoder().encode(usersMiniPutDto.getNewPassword()));
        usersRepository.save(user);
        return true;
    }

    @Transactional
    public boolean sendResetPasswordEmail(String email) {
        ResetPasswordEmailMessage message = new ResetPasswordEmailMessage(email);
        rabbitTemplate.convertAndSend("ResetPasswordEmail", message);
        return true;
    }

    @Transactional
    @RabbitListener(queues = "ResetPasswordEmail")
    public void handleResetPasswordEmail(ResetPasswordEmailMessage message) throws Exception {
        Optional<Users> user = usersRepository.findByEmail(message.getEmail());

        if (user.isPresent()) {

            String encode = Util.UrlEncoder(Config.JwtPrefix + Util.generateJwt(new JwtUser(user.get())));

            Util.createMail(
                    Config.fromEmail,
                    message.getEmail(),
                    "Reset password",
                    Config.ResetPasswordContext +
                            domainName + Config.ResetPasswordUrl + encode);
        }
    }

    @Transactional
    public boolean resetPassword(UUID usersId, UsersPasswordPutDto password) {
        Users user = usersRepository.findById(usersId)
                                    .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));

        user.setPassword(Singleton.passwordEncoder().encode(password.getPassword()));
        usersRepository.save(user);
        return true;
    }

    @Transactional
    public UsersGetDto getUserByUsername(String username) {
        Optional<Users> targetUsers = usersRepository.findByUsername(username);
        return usersMapper.fromEntity(targetUsers.get());
    }

    public List<UsersGetDto> getUserByContainingString(String string) {
        List<Users> users = usersRepository.findByUsernameContainingIgnoreCase(string);
        List<UsersGetDto> usersGetDto = new ArrayList<>();
        for (Users user : users) {
            usersGetDto.add(usersMapper.fromEntity(user));
        }
        return usersGetDto;
    }

    @Transactional
    public List<UsersGetDto> getAllUsers() {
        return usersRepository.findAll().stream()
                              .map(usersMapper::fromEntity)
                              .collect(Collectors.toList());
    }

    @Transactional
    public void changeSingleUserRole(UsersGetDto inputUserGetDto) {
        Users userToUpdate = usersRepository.findById(inputUserGetDto.getId()).get();
        Roles newRoleOfUser = rolesRepository.findByRoleName(inputUserGetDto.getRole().getRoleName()).orElseThrow();
        userToUpdate.setRole(newRoleOfUser);
        usersRepository.save(userToUpdate);
    }

    @Transactional
    public void changeUsersRoles(List<UsersGetDto> usersGetDtoList) {
        usersGetDtoList.forEach(singleUserGetDto -> changeSingleUserRole(singleUserGetDto));
    }
}
