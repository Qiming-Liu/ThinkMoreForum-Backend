package com.thinkmore.forum.controller;

import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/api/v1/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @GetMapping(path = "/hello_world")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping(path = "/get_jwt_context")
    public Object getCurrentUsersId() {
        return Util.getJwtContext();
    }
}