package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.EmailPutDto;
import com.thinkmore.forum.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/profile")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PutMapping("/change_email")
    public ResponseEntity<String> modify(@RequestBody EmailPutDto emailPutDto){
        return ResponseEntity.ok(emailService.changeEmail(emailPutDto));
    }
}