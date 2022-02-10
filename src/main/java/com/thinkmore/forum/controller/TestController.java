package com.thinkmore.forum.controller;

import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @GetMapping(path = "/jwt-context")
    public Object jwtContext() {
        return Util.getJwtContext();
    }
}