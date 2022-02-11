package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "/v1/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    @GetMapping(path = "/jwt-context")
    public ResponseEntity<ArrayList<String>> jwtContext() {
        return ResponseEntity.ok(Util.getJwtContext());
    }
}