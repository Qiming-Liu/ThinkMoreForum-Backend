package com.thinkmore.forum.entity.websocket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OnlineMessage {
    private String username;
    private String status;
}
