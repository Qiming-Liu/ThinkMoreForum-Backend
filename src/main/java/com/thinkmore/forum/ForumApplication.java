package com.thinkmore.forum;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@Slf4j
@SpringBootApplication
public class ForumApplication{

    public static void main(String[] args){
        ConfigurableApplicationContext application = SpringApplication.run(ForumApplication.class, args);

        Environment env = application.getEnvironment();
        String port = env.getProperty("server.port");
        port = port == null ? "8080" : port;
        String path = env.getProperty("server.servlet.context-path");
        path = path == null ? "" : path;
        log.info("\n----------------------------------------------------------\n\t" +
                "Application Demo is running! Access URLs:\n\t" +
                "Local:   \thttp://localhost:" + port + path + "/\n\t" +
                "Swagger: \thttp://localhost:" + port + path + "/swagger-ui/\n\t" +
//                "H2:      \thttp://localhost:" + port + path + "/h2-console/\n" +
                "----------------------------------------------------------");

    }

}
