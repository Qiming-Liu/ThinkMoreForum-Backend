package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.follower.FollowerGetDto;
import com.thinkmore.forum.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/follower")
@RequiredArgsConstructor
public class FollowerController {
    private final FollowerService followerService;

//  view fans
    @GetMapping(path = "/view_follower")
    public ResponseEntity<List<FollowerGetDto>> view_follower(@RequestParam UUID Id) {
        return ResponseEntity.ok(followerService.getFollowersById(Id));
    }

    @GetMapping(path = "/view_followed_user")
    public ResponseEntity<List<FollowerGetDto>> view_followed_user(@RequestParam UUID Id) {
        return ResponseEntity.ok(followerService.getFriendsById(Id));
    }

    @PostMapping(path = "/follow_user")
    public ResponseEntity<FollowerGetDto> follow_user(@RequestParam UUID Id) {
        return ResponseEntity.ok(followerService.followUsers(Id));
    }
}
