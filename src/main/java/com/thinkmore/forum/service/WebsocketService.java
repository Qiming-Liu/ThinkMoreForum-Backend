package com.thinkmore.forum.service;

import com.thinkmore.forum.websocket.OnlineMsg;
import com.thinkmore.forum.websocket.ReminderMessage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebsocketService {
    private final JedisPool jedisPool;
    private final SimpMessagingTemplate simpMessagingTemplate;

    private final String usernameRedisHeader = "username:";
    private final String sessionIdRedisHeader = "sessionId:";

    public List<String> signOnline(String sessionId, OnlineMsg onlineMsg) {

        List<String> allAvailableKeyList = new ArrayList<>();

        try (Jedis jedis = jedisPool.getResource()) {
            if (onlineMsg.getUsername().length() > 0) {
                jedis.set(usernameRedisHeader + onlineMsg.getUsername(), onlineMsg.getStatus());
                jedis.set(sessionIdRedisHeader + sessionId, onlineMsg.getUsername());
            }
        } catch (Error error) {
            log.info("Failed to set user to online");
            throw new RuntimeException(error);
        }

        try (Jedis jedis = jedisPool.getResource()) {
            Set<String> allAvailableKeys = jedis.keys(usernameRedisHeader + "*");
            allAvailableKeyList.addAll(allAvailableKeys);
        } catch (Error error) {
            log.info("Failed to get online users");
            throw new RuntimeException(error);
        }

        return allAvailableKeyList;
    }

    public ReminderMessage forwardReminder(ReminderMessage reminder){
        simpMessagingTemplate.convertAndSendToUser(reminder.getRecipient(), "/reminded", reminder);
        return reminder;
    }

    public void signOffline(SessionDisconnectEvent event) {
        String userSessionId = event.getSessionId();
        try (Jedis jedis = jedisPool.getResource()) {
            String username = jedis.get(sessionIdRedisHeader + userSessionId);
            jedis.del(usernameRedisHeader + username);
            jedis.del(sessionIdRedisHeader + userSessionId);
        } catch (Error error) {
            System.out.println("Failed to set user to offline");
        }

        List<String> onlineUserList = new ArrayList<>();

        try (Jedis jedis = jedisPool.getResource()) {
            Set<String> onlineUserSet = jedis.keys(usernameRedisHeader + "*");
            onlineUserList.addAll(onlineUserSet);
            this.simpMessagingTemplate.convertAndSend("/hall/greetings", onlineUserList);
        } catch (Error error) {
            System.out.println("Failed to broadcast online users list");
        }
    }
}
