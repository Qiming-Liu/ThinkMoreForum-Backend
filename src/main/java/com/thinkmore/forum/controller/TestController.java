package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.test.TestGetDto;
import com.thinkmore.forum.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;

    @GetMapping(path = "/echo")
    public String getEcho() {
        return "Echo 1.. 2.. 3..";
    }

    @GetMapping(path = "/get_user")
    public ResponseEntity<List<TestGetDto>> get_user(@RequestParam UUID userId) {
        return ResponseEntity.ok(testService.getUsersById(userId));
    }
}