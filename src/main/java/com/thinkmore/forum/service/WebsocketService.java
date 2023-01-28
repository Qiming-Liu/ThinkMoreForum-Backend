package com.thinkmore.forum.service;

import com.thinkmore.forum.entity.redis.OnlineUser;
import com.thinkmore.forum.entity.websocket.OnlineMessage;
import com.thinkmore.forum.entity.websocket.ReminderMessage;
import com.thinkmore.forum.repository.OnlineUsersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebsocketService {
    private final OnlineUsersRepository onlineUsersRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public List<String> getOnlineUser() {
        List<OnlineUser> onlineUserList = onlineUsersRepository.findAll();
        Set<String> onlineUserSet = new HashSet<>();
        if (onlineUserList.size() > 0) {
            onlineUserList.forEach(onlineUser -> {
                if (onlineUser != null) {
                    onlineUserSet.add(onlineUser.getUsername());
                }
            });
            return new ArrayList<>(onlineUserSet);
        } else {
            return new ArrayList<>();
        }
    }

    @Transactional
    public List<String> signOnline(String sessionId, OnlineMessage onlineMsg) {

        if (onlineMsg.getUsername().length() > 0) {
            OnlineUser onlineUser = new OnlineUser();
            onlineUser.setId(sessionId);
            onlineUser.setUsername(onlineMsg.getUsername());
            onlineUsersRepository.save(onlineUser);
        }

        return getOnlineUser();
    }

    public ReminderMessage forwardReminder(ReminderMessage reminder) {
        simpMessagingTemplate.convertAndSendToUser(reminder.getRecipient(), "/reminded", reminder);
        return reminder;
    }

    @Transactional
    public void signOffline(SessionDisconnectEvent event) {
        onlineUsersRepository.findById(event.getSessionId()).ifPresent(onlineUsersRepository::delete);
        this.simpMessagingTemplate.convertAndSend("/hall/greetings", getOnlineUser());
    }
}
