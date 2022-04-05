package com.thinkmore.forum.entity.redis;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "OnlineUser", timeToLive = 86400)
@Getter
@Setter
public class OnlineUser {

    @Id
    @Column(name = "id", nullable = false)
    String Id;
    @Column(name = "username", nullable = false)
    String username;
}
