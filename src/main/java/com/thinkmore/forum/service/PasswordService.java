package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.PasswordPutDto;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.exception.InvalidOldPasswordException;
import com.thinkmore.forum.exception.UserNotFoundException;
import com.thinkmore.forum.repository.PasswordRepository;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordService {

    private final PasswordRepository passwordRepository;

    public String changePassword(PasswordPutDto passwordPutDto) {
        String users_id = Util.getJwtContext().get(0);
        UUID id = UUID.fromString(users_id);

        Users user = passwordRepository.findByUsersId(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + users_id));

        if (!Util.match(passwordPutDto.getOldPassword(), user.getPassword())) {
            throw new InvalidOldPasswordException("Old password is wrong");
        }

        user.setPassword(Util.passwordEncoder(passwordPutDto.getNewPassword()));
        passwordRepository.save(user);
        return "You have successfully changed your password!";
    }
}
