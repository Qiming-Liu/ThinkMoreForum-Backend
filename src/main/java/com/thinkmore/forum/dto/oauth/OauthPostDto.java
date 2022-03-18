package com.thinkmore.forum.dto.oauth;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class OauthPostDto implements Serializable {
    private String oauthType;
    private String openid;
}
