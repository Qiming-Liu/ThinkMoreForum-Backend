package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.webSocket.OnlineMsg;
import com.thinkmore.forum.webSocket.ReminderMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/hello")
    @SendTo("/hall/greetings")
    public OnlineMsg rigisterOnline(OnlineMsg onlineMsg) throws Exception {
        return onlineMsg;
    }

    @MessageMapping("/reminder")
    public ReminderMessage forwardReminder(ReminderMessage reminder){
        simpMessagingTemplate.convertAndSendToUser(reminder.getRecipient(), "/reminded", reminder);
        System.out.println(reminder.toString());
        return reminder;
    }

}