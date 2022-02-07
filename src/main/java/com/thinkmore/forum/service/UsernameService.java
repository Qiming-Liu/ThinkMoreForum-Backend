package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.UsernamePutDto;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.exception.InvalidOldUsernameException;
import com.thinkmore.forum.exception.UserNotFoundException;
import com.thinkmore.forum.repository.UsernameRepository;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsernameService {

    private final UsernameRepository usernameRepository;

    public String changeUsername(UsernamePutDto usernamePutDto) {
        String users_id = Util.getJwtContext().get(0);
        UUID id = UUID.fromString(users_id);

        Users user = usernameRepository.findByUsersId(id)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));

        if (!Objects.equals(usernamePutDto.getOldUsername(), user.getUsername())) {
            throw new InvalidOldUsernameException("Username does not exist");
        }

        user.setUsername(usernamePutDto.getNewUsername());
        usernameRepository.save(user);
        return "Username changed";
    }
}
