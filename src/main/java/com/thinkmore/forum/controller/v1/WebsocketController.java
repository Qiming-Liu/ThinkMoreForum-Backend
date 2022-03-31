package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.websocket.OnlineMsg;
import com.thinkmore.forum.websocket.ReminderMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class WebsocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    private JedisPool pool;

    private final String userIdRedisHeader = "userId:";
    private final String sessionIdRedisHeader = "sessionId:";

    @PostConstruct
    public void initialize() {
        this.pool = new JedisPool(this.redisHost, this.redisPort);
    }

    @MessageMapping("/hello")
    @SendTo("/hall/greetings")
    public List<String> rigisterOnline(@Header("simpSessionId") String sessionId, OnlineMsg onlineMsg) throws Exception {

        List<String> allAvailableKeyList = new ArrayList<String>();

        try (Jedis jedis = pool.getResource()) {
            jedis.set(userIdRedisHeader + onlineMsg.getUserId(), onlineMsg.getStatus());
            jedis.set(sessionIdRedisHeader + sessionId, onlineMsg.getUserId());
        } catch (Error error) {
            System.out.println("Failed to set user to online");
        }

        try (Jedis jedis = pool.getResource()) {
            Set<String> allAvailableKeys = jedis.keys(userIdRedisHeader + "*");
            allAvailableKeys.stream()
                    .forEach((key) -> {allAvailableKeyList.add(key);});
        } catch (Error error) {
            System.out.println("Failed to get online users");
        }

        return allAvailableKeyList;
    }

    @MessageMapping("/reminder")
    public ReminderMessage forwardReminder(ReminderMessage reminder){
        simpMessagingTemplate.convertAndSendToUser(reminder.getRecipient(), "/reminded", reminder);
        return reminder;
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent event) {
        String userSessionId = event.getSessionId();
        try (Jedis jedis = pool.getResource()) {
            String userId = jedis.get(sessionIdRedisHeader + userSessionId);
            jedis.del(userIdRedisHeader + userId);
            jedis.del(sessionIdRedisHeader + userSessionId);
        } catch (Error error) {
            System.out.println("Failed to set user to offline");
        }

        List<String> onlineUserList = new ArrayList<String>();

        try (Jedis jedis = pool.getResource()) {
            Set<String> onlineUserSet = jedis.keys(userIdRedisHeader + "*");
            onlineUserSet.stream()
                    .forEach((key) -> {onlineUserList.add(key);});
            this.simpMessagingTemplate.convertAndSend("/hall/greetings", onlineUserList);
        } catch (Error error) {
            System.out.println("Failed to broadcast online users list");
        }
    }
}