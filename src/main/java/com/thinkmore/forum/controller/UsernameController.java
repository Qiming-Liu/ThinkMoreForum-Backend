package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.UsernamePutDto;
import com.thinkmore.forum.service.UsernameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class UsernameController {

    private final UsernameService usernameService;

    @PutMapping("/change_username")
    public ResponseEntity<String> modify(@RequestBody UsernamePutDto usernamePutDto){
        return ResponseEntity.ok(usernameService.changeUsername(usernamePutDto));
    }
}