package com.thinkmore.forum.dto.followerUsers;

import com.thinkmore.forum.dto.users.UsersGetDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class FollowerUsersGetDto implements Serializable {
    private UUID id;

    private UsersGetDto users;

    private UsersGetDto followedUsers;

    private OffsetDateTime createTimestamp;
}
