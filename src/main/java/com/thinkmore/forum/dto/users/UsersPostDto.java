package com.thinkmore.forum.dto.users;

import com.thinkmore.forum.dto.img.ImgPostDto;
import com.thinkmore.forum.dto.roles.RolesPostDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UsersPostDto implements Serializable {
    private UUID id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private ImgPostDto profileImg;
    private RolesPostDto role;
    private OffsetDateTime lastLoginTimestamp;
    private OffsetDateTime createTimestamp;
}
