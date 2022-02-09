package com.thinkmore.forum.configuration;

import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
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
            "/api/v1/password/reset_password_send_email",
    };

    public final static String JwtSecretKey = "fruue37r7yrfhf87f7876guyggf%$$#$%^&%RTHGhjjkj23456rkkkkdkd";
    public final static String JwtPrefix = "";//Bearer ";
    public final static LocalDate ExpireTime = LocalDate.now().plusDays(1);
    public final static PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
    public final static String DefaultRole = "unverified_user";
    public final static Email senderEmail = new Email("jiangjianglovezhou@gmail.com");
    public final static String emailSubject = "Reset Password";
    public final static String verifyEmailSubject = "Verify New Email";
    public final static String url = "http://127.0.0.1:8080/user/changePassword?token=";
//    public final static Content content = new Content("text/plain", "Reset your password!");
    public final static String templateId = "d-100a0215815a45e58cf43751988a468e";
    public final static String emailUrl = "http://172.22.160.1:8080/v1/user/update-email?token=";
    public final static String key = "bf7429164ac97e5dae68b01c9b5f4db2fdf172b5e843d2c2d8f3f829e9b785c02cd38bda9be56537804f8e5626eeca116989564aa0d603c40355d037f4713c55c2638ef376ce12e5455e4dbfd5cd49cc";
    public final static String decodedKey = "1a53d4469f513e1ae3856fc2c603b8d6";
}
