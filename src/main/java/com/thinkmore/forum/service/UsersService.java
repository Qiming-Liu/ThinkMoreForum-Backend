package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.oauth.OauthGetDto;
import com.thinkmore.forum.entity.JwtUser;
import com.thinkmore.forum.configuration.Config;
import com.thinkmore.forum.entity.Oauth;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.exception.InvalidOldPasswordException;
import com.thinkmore.forum.exception.UserHasPasswordException;
import com.thinkmore.forum.exception.UserNotFoundException;
import com.thinkmore.forum.mapper.OauthMapper;
import com.thinkmore.forum.mapper.UsersMapper;
import com.thinkmore.forum.repository.OauthRepository;
import com.thinkmore.forum.repository.RolesRepository;
import com.thinkmore.forum.repository.UsersRepository;
import com.thinkmore.forum.util.Singleton;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final OauthRepository oauthRepository;
    private final UsersMapper usersMapper;
    private final OauthMapper oauthMapper;
    private final RolesRepository rolesRepository;

    //only for jwt
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = usersRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Login Email %s not found", email)));
        return new JwtUser(user);
    }

    @Transactional
    public Boolean register(String email, String username, String password) {
        Util.checkPassword(password);
        Users user = new Users();

        user.setUsername(username);
        user.setPassword(Singleton.passwordEncoder().encode(password));
        user.setEmail(email);
        user.setProfileImg(null);
        user.setRole(rolesRepository.findByRoleName(Config.DefaultRole).orElseThrow());
        user.setLastLoginTimestamp(OffsetDateTime.now());
        user.setCreateTimestamp(OffsetDateTime.now());

        usersRepository.save(user);

        return true;
    }

    @Transactional
    public Boolean thirdPartyLogin(String email, String username, String oauthType, String openid) {
        Users user = new Users();

        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(Singleton.passwordEncoder().encode(openid));
        user.setRole(rolesRepository.findByRoleName(Config.DefaultRole).orElseThrow());
        user.setLastLoginTimestamp(OffsetDateTime.now());
        user.setCreateTimestamp(OffsetDateTime.now());

        usersRepository.save(user);

        Oauth oauth = new Oauth();

        oauth.setUsers(user);
        oauth.setOauthType(oauthType);
        oauth.setOpenid(openid);

        oauthRepository.save(oauth);
        return true;
    }

    @Transactional
    public void updateLastLoginTimestamp(String username) {
        Users user = usersRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username %s not found", username)));
        user.setLastLoginTimestamp(OffsetDateTime.now());
        usersRepository.save(user);
    }

    public boolean uniqueEmail(String email) {
        return usersRepository.findByEmail(email).isEmpty();
    }

    public boolean uniqueUsername(String username) {
        return usersRepository.findByUsername(username).isEmpty();
    }

    public OauthGetDto getUserByOpenId(String openid) {
        return oauthMapper.fromEntity(oauthRepository.findByOpenid(openid).get());
    }

    @Transactional
    public boolean changeUsername(String newUsername) {
        UUID users_id = UUID.fromString(Util.getJwtContext().get(0));

        Users user = usersRepository.findById(users_id)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));

        user.setUsername(newUsername);
        usersRepository.save(user);
        return true;
    }

    @Transactional
    public boolean sendVerificationEmail(String newEmail) throws Exception {

        Util.createMail(
                Config.fromEmail,
                newEmail,
                "Verify Email",
                Config.VerifyEmailContext + Config.VerifyEmailUrl + newEmail);

        return true;
    }

    @Transactional
    public boolean changeEmail(String newEmail) {
        UUID users_id = UUID.fromString(Util.getJwtContext().get(0));
        Users user = usersRepository.findById(users_id)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));

        user.setEmail(newEmail);
        usersRepository.save(user);

        return true;
    }

    @Transactional
    public boolean changePassword(String oldPassword, String newPassword) {
        String users_id = Util.getJwtContext().get(0);
        UUID id = UUID.fromString(users_id);
        Util.checkPassword(oldPassword);
        Util.checkPassword(newPassword);

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + users_id));

        if (!Singleton.passwordEncoder().matches(oldPassword, user.getPassword())) {
            throw new InvalidOldPasswordException("Old password is wrong");
        }

        user.setPassword(Singleton.passwordEncoder().encode(newPassword));
        usersRepository.save(user);
        return true;
    }

    @Transactional
    public boolean sendResetPasswordEmail(String email) throws Exception {
        Optional<Users> user = usersRepository.findByEmail(email);

        if (user.isPresent()) {
            Util.createMail(
                    Config.fromEmail,
                    email,
                    "Reset password",
                    Config.ResetPasswordContext +
                            Util.UrlEncoder(Config.ResetPasswordUrl +
                                    Config.JwtPrefix +
                                    Util.generateJwt(new JwtUser(user.get()))));
        }

        return true;
    }

    @Transactional
    public boolean resetPassword(String password) {
        String users_id = Util.getJwtContext().get(0);
        UUID id = UUID.fromString(users_id);
        Util.checkPassword(password);

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setPassword(Singleton.passwordEncoder().encode(password));
        usersRepository.save(user);
        return true;
    }

    @Transactional
    public boolean setPassword(String password) {
        String users_id = Util.getJwtContext().get(0);
        UUID id = UUID.fromString(users_id);

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.getPassword().equals("")) {
            throw new UserHasPasswordException("The user already has a password!");
        }

        Util.checkPassword(password);
        user.setPassword(Singleton.passwordEncoder().encode(password));
        usersRepository.save(user);
        return true;
    }
}