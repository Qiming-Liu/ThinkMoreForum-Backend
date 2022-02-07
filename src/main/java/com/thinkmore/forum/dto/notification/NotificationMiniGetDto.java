package com.thinkmore.forum.dto.notification;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class NotificationMiniGetDto {
    private UUID id;
    private String context;
}
