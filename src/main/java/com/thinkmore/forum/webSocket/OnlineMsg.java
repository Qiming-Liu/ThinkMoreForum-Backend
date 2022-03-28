package com.thinkmore.forum.webSocket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OnlineMsg {
    private String userId;
    private Boolean online;
}
