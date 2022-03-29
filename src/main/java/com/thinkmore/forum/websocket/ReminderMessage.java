package com.thinkmore.forum.websocket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReminderMessage {
    private String sender;
    private String recipient;
    private String content;
}
