package com.thinkmore.forum.dto.oauth;

import com.thinkmore.forum.dto.users.UsersPostDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class OauthPostDto implements Serializable {
    private UUID id;
    private UsersPostDto users;
    private String oauthType;
    private String openid;
}
