package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.users.UsersGetDto;
import com.thinkmore.forum.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/authentication")
@RequiredArgsConstructor
@Slf4j
public class Authentication {

    private final UsersService usersService;

    @PostMapping(path = "/registration")
    public UsersGetDto registration(@RequestParam String email, @RequestParam String username, @RequestParam String password) {
        return usersService.registration(email, username, password);
    }

    @GetMapping(path = "/unique_email")
    public boolean uniqueEmail(@RequestParam String email) {
        return usersService.uniqueEmail(email);
    }

    @GetMapping(path = "/unique_username")
    public boolean uniqueUsername(@RequestParam String username) {
        return usersService.uniqueUsername(username);
    }
}