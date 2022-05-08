package com.thinkmore.forum;

import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
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
                "ThinkMoreForum Backend is running! Access URLs:\n\t" +
                "Local:   \thttp://localhost:" + port + path + "/\n\t" +
                "Swagger: \thttp://localhost:" + port + path + "/swagger-ui.html\n\t" +
//                "H2:      \thttp://localhost:" + port + path + "/h2-console/\n" +
                "----------------------------------------------------------");
    }
    @Bean
    MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name}") String applicationName) {
        return (registry) -> registry.config().commonTags("application", applicationName);
    }
}
