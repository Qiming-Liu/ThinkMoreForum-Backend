package com.thinkmore.forum.dto.users;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class UsersImgPutDto implements Serializable {
    private String headImgUrl;
    private String profileImgUrl;
}
