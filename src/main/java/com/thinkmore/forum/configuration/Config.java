package com.thinkmore.forum.configuration;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

public class Config {
    // Jwt
    public final static String[] ignoreUrl = new String[]{
            "/swagger-ui**",
            "/v1/users/signup**",
            "/v1/users/unique-email**",
            "/v1/users/unique-username**",
            "/v1/users/reset-password**",
    };
    public final static String JwtSecretKey = "fruue37r7yrfhf87f7876guyggf%$$#$%^&%RTHGhjjkj23456rkkkkdkd";
    public final static String JwtPrefix = "";//Bearer ";
    public final static LocalDate ExpireTime = LocalDate.now().plusDays(1);

    // Password
    public final static PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();

    // Role
    public final static String DefaultRole = "unverified_user";

    // Email
    public final static String DecodedKey = "1a53d4469f513e1ae3856fc2c603b8d6";
    public final static String Apikey = "bf7429164ac97e5dae68b01c9b5f4db2fdf172b5e843d2c2d8f3f829e9b785c02cd38bda9be56537804f8e5626eeca116989564aa0d603c40355d037f4713c55c2638ef376ce12e5455e4dbfd5cd49cc";
    public final static String senderEmail = "jiangjianglovezhou@gmail.com";
    public final static String ResetPasswordUrl = "前端url?token=";
    public final static String ResetPasswordContext = "Please click the link to reset your new password:\n";
    public final static String VerifyEmailUrl = "前端url?token=";
    public final static String VerifyEmailContext = "Please click the link to verify your new email:\n";


}
