package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.users.UsersGetDto;
import com.thinkmore.forum.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UsersController {
    private final UsersService usersService;

    @GetMapping("/{user_id}")
    public ResponseEntity<UsersGetDto> findUserById(@PathVariable("user_id") String user_id) {
        UUID userId = UUID.fromString(user_id);
        UsersGetDto usersGetDto = usersService.getUserById(userId);
        return ResponseEntity.ok(usersGetDto);
    }

    @PutMapping("/password/{old_password}/{new_password}")
    public ResponseEntity<Boolean> modify(@PathVariable String old_password, @PathVariable String new_password){
        return ResponseEntity.ok(usersService.changePassword(old_password, new_password));
    }

    @GetMapping("/reset-password/{email}")
    public ResponseEntity<Boolean> find(@PathVariable String email) throws IOException {
        return ResponseEntity.ok(usersService.checkEmail(email));
    }

    @PutMapping("/reset-password/{new_password}")
    public ResponseEntity<Boolean> modify(@PathVariable String new_password){
        return ResponseEntity.ok(usersService.resetPassword(new_password));
    }
}
