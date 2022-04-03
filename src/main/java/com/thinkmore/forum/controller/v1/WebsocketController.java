package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.service.WebsocketService;
import com.thinkmore.forum.entity.websocket.OnlineMessage;
import com.thinkmore.forum.entity.websocket.ReminderMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebsocketController {

    private final WebsocketService websocketService;

    @MessageMapping("/hello")
    @SendTo("/hall/greetings")
    public List<String> signOnline(@Header("simpSessionId") String sessionId, OnlineMessage onlineMsg) {
        return websocketService.signOnline(sessionId, onlineMsg);
    }

    @MessageMapping("/reminder")
    public ReminderMessage forwardReminder(ReminderMessage reminder) {
        return websocketService.forwardReminder(reminder);
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        websocketService.signOffline(event);
    }
}