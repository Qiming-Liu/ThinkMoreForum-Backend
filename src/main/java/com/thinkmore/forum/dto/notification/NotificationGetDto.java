package com.thinkmore.forum.dto.notification;

import com.thinkmore.forum.dto.users.UsersMiniGetDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NotificationGetDto implements Serializable {
    private UUID id;
    private UsersMiniGetDto users;
    private String icon;
    private String context;
    private Boolean viewed;
    private OffsetDateTime createTimestamp;
}
