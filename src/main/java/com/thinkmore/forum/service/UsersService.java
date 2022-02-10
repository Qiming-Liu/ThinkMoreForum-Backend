package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.users.UsersGetDto;
import com.thinkmore.forum.entity.JwtUser;
import com.thinkmore.forum.configuration.Config;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.exception.InvalidOldPasswordException;
import com.thinkmore.forum.exception.UserNotFoundException;
import com.thinkmore.forum.mapper.UsersMapper;
import com.thinkmore.forum.repository.RolesRepository;
import com.thinkmore.forum.repository.UsersRepository;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersService implements UserDetailsService {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final UsersMapper usersMapper;

    //only for jwt
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username %s not found", username)));
        return new JwtUser(user);
    }

    @Transactional
    public Boolean signup(String email, String username, String password) {
        Users user = new Users();

        user.setUsername(username);
        user.setPassword(Util.passwordEncoder(password));
        user.setEmail(email);
        user.setProfileImg(null);
        user.setRole(rolesRepository.findByRoleName(Config.DefaultRole).orElseThrow());
        user.setLastLoginTimestamp(OffsetDateTime.now());
        user.setCreateTimestamp(OffsetDateTime.now());

        usersRepository.save(user);

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

    public UsersGetDto getUserById(UUID userId) {
        return usersMapper.fromEntity(usersRepository.findById(userId).get());
    }

    @Transactional
    public boolean changePassword(String oldPassword, String newPassword) {
        String users_id = Util.getJwtContext().get(0);
        UUID id = UUID.fromString(users_id);

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + users_id));

        if (!Util.match(oldPassword, user.getPassword())) {
            throw new InvalidOldPasswordException("Old password is wrong");
        }

        user.setPassword(Util.passwordEncoder(newPassword));
        usersRepository.save(user);
        return true;
    }

    @Transactional
    public boolean resetPassword(String password) {
        String users_id = Util.getJwtContext().get(0);
        UUID id = UUID.fromString(users_id);

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setPassword(Util.passwordEncoder(password));
        usersRepository.save(user);
        return true;
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
    public boolean sendResetPasswordEmail(String email) throws IOException {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Email address not found: " + email));

        Util.createMail(
                Config.senderEmail,
                email,
                "Reset password",
                Config.ResetPasswordContext + Config.ResetPasswordUrl + Util.createJwtToken(user));


        return true;
    }

    @Transactional
    public boolean sendVerificationEmail(String newEmail) throws IOException {

        Util.createMail(
                Config.senderEmail,
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
}
