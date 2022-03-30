package com.thinkmore.forum.websocket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OnlineMsg {
    private String userId;
    private String status;
}
