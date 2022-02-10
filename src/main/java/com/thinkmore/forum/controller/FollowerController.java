package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.follower.FollowerGetDto;
import com.thinkmore.forum.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/follower")
@RequiredArgsConstructor
public class FollowerController {
    private final FollowerService followerService;

    @GetMapping(path = "/follow_user")
    public ResponseEntity<List<FollowerGetDto>> follow_user(@RequestParam UUID Id) {
        return ResponseEntity.ok(followerService.getFollowersById(Id));
    }

    @GetMapping(path = "/friends")
    public ResponseEntity<List<FollowerGetDto>> friends(@RequestParam UUID Id) {
        return ResponseEntity.ok(followerService.getFriendsById(Id));
    }

    @PostMapping(path = "/follow")
    public ResponseEntity<FollowerGetDto> follow(@RequestParam UUID Id) {
        return ResponseEntity.ok(followerService.followUsers(Id));
    }
}
