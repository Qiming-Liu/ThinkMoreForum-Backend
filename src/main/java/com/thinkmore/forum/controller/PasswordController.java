package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.PasswordPutDto;
import com.thinkmore.forum.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/password")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @PutMapping("/change_password")
    public ResponseEntity<String> modify(@RequestParam PasswordPutDto passwordPutDto){
        return ResponseEntity.ok(passwordService.changePassword(passwordPutDto));
    }
}
