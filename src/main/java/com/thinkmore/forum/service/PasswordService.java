package com.thinkmore.forum.service;

import com.sendgrid.helpers.mail.objects.Email;
import com.thinkmore.forum.configuration.Config;
import com.thinkmore.forum.dto.PasswordPutDto;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.exception.InvalidOldPasswordException;
import com.thinkmore.forum.exception.UserNotFoundException;
import com.thinkmore.forum.repository.PasswordRepository;
import com.thinkmore.forum.util.Util;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordRepository passwordRepository;
    private final SecretKey secretKey;

    public boolean changePassword(PasswordPutDto passwordPutDto) {
        String users_id = Util.getJwtContext().get(0);
        UUID id = UUID.fromString(users_id);

        Users user = passwordRepository.findByUsersId(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + users_id));

        if (!Util.match(passwordPutDto.getOldPassword(), user.getPassword())) {
            throw new InvalidOldPasswordException("Old password is wrong");
        }

        user.setPassword(Util.passwordEncoder(passwordPutDto.getNewPassword()));
        passwordRepository.save(user);
        return true;
    }

    public boolean checkEmail(String email) throws IOException {
        Users user = passwordRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Email address not found: " + email ));
        String jwtToken = Jwts.builder()
                .setId(user.getId() + "")
                .setSubject(user.getRole().getRoleName())
                .setAudience(user.getRole().getPermission())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(Config.ExpireTime))
                .signWith(secretKey)
                .compact();
        Email emailAddress = new Email(email);
        Util.sendMail(emailAddress, jwtToken);
        return true;
    }

    public boolean resetPassword(String password){
        String users_id = Util.getJwtContext().get(0);
        UUID id = UUID.fromString(users_id);

        Users user = passwordRepository.findByUsersId(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setPassword(Util.passwordEncoder(password));
        passwordRepository.save(user);
        return true;
    }
}
