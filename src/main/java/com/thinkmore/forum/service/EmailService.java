package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.EmailPutDto;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.exception.InvalidOldEmailException;
import com.thinkmore.forum.exception.UserNotFoundException;
import com.thinkmore.forum.repository.EmailRepository;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailRepository emailRepository;

    public String changeEmail(EmailPutDto emailPutDto) {
        String users_id = Util.getJwtContext().get(0);
        UUID id = UUID.fromString(users_id);

        Users user = emailRepository.findByUsersId(id)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));

        if (!Objects.equals(emailPutDto.getOldEmail(), user.getEmail())) {
            throw new InvalidOldEmailException("Email address does not exist");
        }

        user.setEmail(emailPutDto.getNewEmail());
        emailRepository.save(user);
        return "Email changed.";
    }
}
