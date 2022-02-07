package com.thinkmore.forum.dto.follower;

import com.thinkmore.forum.entity.Users;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class FollowerGetDto {
    private UUID id;

    private Users users;

    private Users followedUsers;

    private OffsetDateTime createTimestamp;
}
