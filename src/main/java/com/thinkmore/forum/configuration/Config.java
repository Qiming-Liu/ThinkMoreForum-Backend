package com.thinkmore.forum.configuration;

import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

public class Config {
    public final static String[] ignoreUrl = new String[]{
            "/api/v1/test/hello_world",
            "/swagger-ui/**",
            "/api/v1/authentication/registration",
            "/api/v1/authentication/unique_email",
            "/api/v1/password/reset_password_send_email",
            "/**"
    };

    public final static String JwtSecretKey = "fruue37r7yrfhf87f7876guyggf%$$#$%^&%RTHGhjjkj23456rkkkkdkd";
    public final static String JwtPrefix = "";//Bearer ";
    public final static LocalDate ExpireTime = LocalDate.now().plusDays(1);
    public final static PasswordEncoder passwordEncoder = new Argon2PasswordEncoder();
    public final static String DefaultRole = "unverified_user";
    public final static Email senderEmail = new Email("alfred.minjiang@gmail.com");
    public final static String emailSubject = "Reset Password";
    public final static Content content = new Content("text/plain", "Reset your password!");
    public final static String templateId = "d-100a0215815a45e58cf43751988a468e";
    public final static String url = "http://127.0.0.1:8080/user/changePassword?token=";
    public final static String key = "160e9fecf26393c13070772433a9d61ad6ca2421e8ddea65633fe38a1bc6421439978ac581e44a16fe216477bb288b7e94c2a6dd071e2f185c6ac683f703596603319f968d1f2d16c566b4497853eda6";
    public final static String decodedKey = "05b09885b1065e215aaddbce86399e3f";
}
