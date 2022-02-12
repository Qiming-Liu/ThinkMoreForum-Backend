package com.thinkmore.forum.dto.follower;

import com.thinkmore.forum.dto.users.UsersPostDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class FollowerPostDto {
    private UUID id;

    private UsersPostDto usersPostDto;

    private UsersPostDto followedUsersPostDto;

    private OffsetDateTime createTimestamp;
}
