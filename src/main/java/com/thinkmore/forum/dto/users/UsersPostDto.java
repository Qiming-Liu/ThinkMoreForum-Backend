package com.thinkmore.forum.dto.users;

import com.thinkmore.forum.dto.roles.RolesPostDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UsersPostDto implements Serializable {
    private String username;
    private String email;
    private String password;
}
