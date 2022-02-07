package com.thinkmore.forum.dto.notification;

import com.thinkmore.forum.dto.users.UsersGetDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NotificationMiniGetDto {
    private UUID id;
    private String context;
}
