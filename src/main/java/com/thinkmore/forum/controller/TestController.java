package com.thinkmore.forum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @RequestMapping(value="/authentication/unique_username",method= RequestMethod.GET)
    public String virtify_name(@RequestParam("name") String name) {
        return "the name is:" + name;

    }

    @RequestMapping(value="/authentication/unique_email",method= RequestMethod.GET)
    public String virtify_email(@RequestParam("email") String email) {
        return "the email is:" + email;

    }

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