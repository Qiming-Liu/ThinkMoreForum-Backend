package com.thinkmore.forum.dto.users;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UsersMiniGetDto implements Serializable {
    private UUID id;
    private String headImgUrl;
    private String username;
}
