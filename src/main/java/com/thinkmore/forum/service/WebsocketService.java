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
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebsocketService {
    private final OnlineUsersRepository onlineUsersRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Transactional
    public List<String> signOnline(String sessionId, OnlineMessage onlineMsg) {

        if (onlineMsg.getUsername().length() > 0) {
            if(onlineUsersRepository.findByUsername(onlineMsg.getUsername()).isEmpty()){
                OnlineUser onlineUser = new OnlineUser();
                onlineUser.setId(sessionId);
                onlineUser.setUsername(onlineMsg.getUsername());
                onlineUsersRepository.save(onlineUser);
            }
        }

        List<OnlineUser> onlineUserList = onlineUsersRepository.findAll();
        return onlineUserList.stream().map(OnlineUser::getUsername).collect(Collectors.toList());
    }

    @Transactional
    public ReminderMessage forwardReminder(ReminderMessage reminder){
        simpMessagingTemplate.convertAndSendToUser(reminder.getRecipient(), "/reminded", reminder);
        return reminder;
    }

    @Transactional
    public void signOffline(SessionDisconnectEvent event) {
        onlineUsersRepository.findById(event.getSessionId()).ifPresent(onlineUsersRepository::delete);

        List<OnlineUser> onlineUserList = onlineUsersRepository.findAll();
        this.simpMessagingTemplate.convertAndSend("/hall/greetings", onlineUserList.stream().map(OnlineUser::getUsername).collect(Collectors.toList()));
    }
}
