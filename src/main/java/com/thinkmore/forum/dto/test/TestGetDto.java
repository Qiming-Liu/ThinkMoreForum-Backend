package com.thinkmore.forum.dto.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestGetDto {
    private UUID id;

    private UUID users;

    private UUID followedUsers;

    private OffsetDateTime createTimestamp;
}
