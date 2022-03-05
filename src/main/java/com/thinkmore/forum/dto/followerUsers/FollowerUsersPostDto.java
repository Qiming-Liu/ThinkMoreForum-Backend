package com.thinkmore.forum.dto.followerUsers;

import com.thinkmore.forum.dto.users.UsersPostDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class FollowerUsersPostDto {
    private UUID id;

    private UsersPostDto usersPostDto;

    private UsersPostDto followedUsersPostDto;

    private OffsetDateTime createTimestamp;
}
