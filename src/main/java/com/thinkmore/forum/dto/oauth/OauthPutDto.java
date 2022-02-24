package com.thinkmore.forum.dto.oauth;

import com.thinkmore.forum.dto.users.UsersPutDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class OauthPutDto implements Serializable {
    private UUID id;
    private UsersPutDto users;
    private String oauthType;
    private String openid;
}
