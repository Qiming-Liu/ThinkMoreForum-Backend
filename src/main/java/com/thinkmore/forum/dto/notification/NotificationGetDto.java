package com.thinkmore.forum.dto.notification;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.thinkmore.forum.dto.users.UsersMiniGetDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationGetDto implements Serializable {
    private UUID id;
    private UsersMiniGetDto triggerUsers;
    private UsersMiniGetDto notifyUsers;
    private String context;
    private Boolean viewed;
    private OffsetDateTime createTimestamp;
}
