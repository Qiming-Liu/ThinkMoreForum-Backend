package com.thinkmore.forum.configuration;

public class StaticConfig {
    public final static String[] ignoreUrl = new String[] {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Prometheus Metrics
            "/actuator/**",
            // -- API
            "/v1/public/**",
    };

    // Role
    public final static String DefaultRole = "general_user";

    // Email
    public final static String DecodedKey = "1a53d4469f513e1ae3856fc2c603b8d6";
    public final static String Apikey = "bf7429164ac97e5dae68b01c9b5f4db2fdf172b5e843d2c2d8f3f829e9b785c02cd38bda9be56537804f8e5626eeca116989564aa0d603c40355d037f4713c55c2638ef376ce12e5455e4dbfd5cd49cc";
    public final static String fromEmail = "jiangjianglovezhou@gmail.com";
    public final static String ResetPasswordUrl = "/password-reset?token=";
    public final static String ResetPasswordContext = "Please click the link to reset your new password:\n";
    public final static String VerifyEmailUrl = "/verify-email?token=";
    public final static String VerifyEmailContext = "Please click the link to verify your new email:\n";

    // Oss
    public final static String BucketName = "image";

    // JWT
    public final static String JwtSecretKey = "https://github.com/Qiming-Liu/ThinkMoreForum-Backend";
    public final static String JwtPrefix = "Bearer ";
}
