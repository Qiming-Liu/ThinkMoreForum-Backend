package com.thinkmore.forum.dto.notification;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NotificationGetDto implements Serializable {
    private UUID id;
    private String imgUrl;
    private String context;
    private Boolean viewed;
    private OffsetDateTime createTimestamp;
}
