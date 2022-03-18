package com.thinkmore.forum.dto.users;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UsersPostDto implements Serializable {
    private String username;
    private String email;
    private String password;
}
