package com.thinkmore.forum.util;

import com.thinkmore.forum.configuration.Config;
import io.jsonwebtoken.security.Keys;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Configuration
public class Singleton {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    public final static SecretKey secretKey = Keys.hmacShaKeyFor(Config.JwtSecretKey.getBytes(StandardCharsets.UTF_8));

    @Bean
    public static MinioClient minioClient() throws Exception {
        MinioClient minioClient = MinioClient.builder().endpoint(Config.OssUrl)
                .credentials(Config.MINIO_ROOT_USER, Config.MINIO_ROOT_PASSWORD).build();

        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(Config.BucketName).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(Config.BucketName).build());
        }

        String policyJson = "{\"Version\":\"2012-10-17\",\"Statement\":[{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:ListBucketMultipartUploads\",\"s3:GetBucketLocation\",\"s3:ListBucket\"],\"Resource\":[\"arn:aws:s3:::image\"]},{\"Effect\":\"Allow\",\"Principal\":{\"AWS\":[\"*\"]},\"Action\":[\"s3:AbortMultipartUpload\",\"s3:DeleteObject\",\"s3:GetObject\",\"s3:ListMultipartUploadParts\",\"s3:PutObject\"],\"Resource\":[\"arn:aws:s3:::image/*\"]}]}";
        minioClient.setBucketPolicy(
                SetBucketPolicyArgs.builder().bucket(Config.BucketName).config(policyJson).build());

        return minioClient;
    }
}
