package com.thinkmore.forum.dto.users;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UsersMiniGetDto {
    private UUID id;
    private String profileImgUrl;
    private String username;
}
