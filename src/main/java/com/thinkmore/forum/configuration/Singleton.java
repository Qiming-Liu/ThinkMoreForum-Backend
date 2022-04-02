package com.thinkmore.forum.configuration;

import io.jsonwebtoken.security.Keys;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;

@EnableRabbit
@Configuration
public class Singleton {

    @Value("${minio.username}")
    private String minioUsername;

    @Value("${minio.password}")
    private String minioPassword;

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setJmxEnabled(false);
        return new JedisPool(config, redisHost, redisPort);
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    public final static SecretKey secretKey = Keys.hmacShaKeyFor(Config.JwtSecretKey.getBytes(StandardCharsets.UTF_8));

    @Bean
    public MinioClient minioClient() throws Exception {
        MinioClient minioClient = MinioClient.builder().endpoint(minioUrl)
                                             .credentials(minioUsername, minioPassword).build();

        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(Config.BucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(Config.BucketName).build());
        }

        String policyJson = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:ListBucketMultipartUploads\",\"s3:GetBucketLocation\",\"s3:ListBucket\"],\"Resource\":[\"arn:aws:s3:::image\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:AbortMultipartUpload\",\"s3:DeleteObject\",\"s3:GetObject\",\"s3:ListMultipartUploadParts\",\"s3:PutObject\"],\"Resource\":[\"arn:aws:s3:::image/*\"]}]}";
        minioClient.setBucketPolicy(
                SetBucketPolicyArgs.builder().bucket(Config.BucketName).config(policyJson).build());

        return minioClient;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization")
                        .exposedHeaders("Access-Control-Allow-Headers", "Authorization, x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers")
                        .allowedOrigins("*");
            }
        };
    }
}
