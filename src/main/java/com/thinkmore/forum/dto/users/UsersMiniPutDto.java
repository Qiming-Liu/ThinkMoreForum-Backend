package com.thinkmore.forum.dto.users;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UsersMiniPutDto implements Serializable {
    private String oldPassword;
    private String newPassword;
}
