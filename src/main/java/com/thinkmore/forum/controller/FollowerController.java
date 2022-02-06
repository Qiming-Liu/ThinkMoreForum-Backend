package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.follower.FollowerGetDto;
import com.thinkmore.forum.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/follower")
@RequiredArgsConstructor
public class FollowerController {
    private final FollowerService followerService;

    @GetMapping(path = "/follow_user")
    public ResponseEntity<List<FollowerGetDto>> follow_user(@RequestParam UUID Id) {
        int i = 1;
        return ResponseEntity.ok(followerService.getFollowersById(Id));
    }
}
