package com.thinkmore.forum.configuration;

import java.time.LocalDate;

public class Config {
    public final static String[] ignoreUrl = new String[]{
            "/api/v1/test/hello_world",
            "/**"};

    public final static String JwtSecretKey = "fruue37r7yrfhf87f7876guyggf%$$#$%^&%RTHGhjjkj23456rkkkkdkd";
    public final static String JwtPrefix = "";//Bearer ";
    public final static LocalDate ExpireTime = LocalDate.now().plusDays(1);
}
