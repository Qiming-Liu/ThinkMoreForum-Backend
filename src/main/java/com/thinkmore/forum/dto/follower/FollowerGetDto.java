package com.thinkmore.forum.dto.follower;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class FollowerGetDto {
    private UUID id;

    private UUID users;

    private UUID followedUsers;

    private OffsetDateTime createTimestamp;
}
