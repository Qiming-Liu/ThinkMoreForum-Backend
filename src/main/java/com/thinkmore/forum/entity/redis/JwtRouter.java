package com.thinkmore.forum.entity.redis;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "JwtRouter", timeToLive = 86400)
@Getter
@Setter
public class JwtRouter {

    @Id
    @Column(name = "fakeJwt", nullable = false)
    String fakeJwt;
    @Column(name = "realJwt", nullable = false)
    String realJwt;
}
