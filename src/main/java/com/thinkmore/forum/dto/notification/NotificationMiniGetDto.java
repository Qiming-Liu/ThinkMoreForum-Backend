package com.thinkmore.forum.dto.notification;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class NotificationMiniGetDto implements Serializable {
    private UUID id;
    private String context;
}
