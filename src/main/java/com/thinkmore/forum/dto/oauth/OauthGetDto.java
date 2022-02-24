package com.thinkmore.forum.dto.oauth;

import com.thinkmore.forum.dto.users.UsersGetDto;
import com.thinkmore.forum.dto.users.UsersMiniGetDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class OauthGetDto implements Serializable {
    private UUID id;
    private UsersGetDto users;
    private String oauthType;
    private String openid;
}
