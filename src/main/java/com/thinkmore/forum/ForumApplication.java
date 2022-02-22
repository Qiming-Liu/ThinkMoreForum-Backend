package com.thinkmore.forum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ForumApplication{

    public static void main(String[] args) {
        SpringApplication.run(ForumApplication.class, args);
    }

}
