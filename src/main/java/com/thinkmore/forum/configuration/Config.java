package com.thinkmore.forum.configuration;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

public class Config {
    public final static String[] ignoreUrl = new String[]{
            "/**",
            "/api/v1/test/hello_world",
            "/swagger-ui/#**",
            "/api/v1/authentication/registration",
            "/api/v1/authentication/unique_email",
    };

    public final static String JwtSecretKey = "fruue37r7yrfhf87f7876guyggf%$$#$%^&%RTHGhjjkj23456rkkkkdkd";
    public final static String JwtPrefix = "";//Bearer ";
    public final static LocalDate ExpireTime = LocalDate.now().plusDays(1);
    public final static PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
    public final static String DefaultRole = "unverified_user";
}
