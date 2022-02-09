package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.PasswordPutDto;
import com.thinkmore.forum.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/password")
@RequiredArgsConstructor
public class PasswordController {

    private final PasswordService passwordService;

    @PutMapping("/change_password")
    public ResponseEntity<Boolean> modify(@RequestBody PasswordPutDto passwordPutDto){
        return ResponseEntity.ok(passwordService.changePassword(passwordPutDto));
    }

    @GetMapping("/reset_password_send_email")
    public ResponseEntity<Boolean> find(@RequestParam String email) throws IOException {
        return ResponseEntity.ok(passwordService.checkEmail(email));
    }

    @PutMapping("reset_password")
    public ResponseEntity<Boolean> modify(@RequestParam String password){
        return ResponseEntity.ok(passwordService.resetPassword(password));
    }
}
