package com.thinkmore.forum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @GetMapping(path = "/hello_world")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping(path = "/get_current_user_id")
    public Object getCurrentUsersId() {
        return getJwt();
    }

    public static Object getJwt() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}