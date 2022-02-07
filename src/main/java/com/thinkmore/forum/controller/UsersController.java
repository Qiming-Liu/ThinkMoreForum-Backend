package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.followPost.FollowPostGetDto;
import com.thinkmore.forum.dto.users.UsersGetDto;
import com.thinkmore.forum.service.FollowPostService;
import com.thinkmore.forum.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/users")
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
}
