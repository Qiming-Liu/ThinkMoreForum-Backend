package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.follower.FollowerGetDto;
import com.thinkmore.forum.service.FollowerService;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/users")
@RequiredArgsConstructor
public class FollowerController {
    private final FollowerService followerService;

//  view fans
    @GetMapping(path = "/follower/{user_id}/{username}")
    public ResponseEntity<List<FollowerGetDto>> view_follower(@PathVariable("user_id") UUID current_UserId, @PathVariable("username") String target_username) {
    return ResponseEntity.ok(followerService.getFollowersByUsername(target_username));
}

    @GetMapping(path = "/followed/{user_id}/{username}")
    public ResponseEntity<List<FollowerGetDto>> view_followed_user(@PathVariable("user_id") UUID current_UserId, @PathVariable("username") String target_username) {
        return ResponseEntity.ok(followerService.getFriendsByUsername(target_username));
    }

    @PostMapping(path = "/follow/{username}")
    public ResponseEntity<FollowerGetDto> follow_user(@PathVariable("username") String username) {
        return ResponseEntity.ok(followerService.followUsers(username));
    }

    @DeleteMapping(path = "/follow/{username}")
    public ResponseEntity<?> unfollow_user(@PathVariable("username") String target_username) {
        String username = Util.getJwtContext().get(1);
        try {
            followerService.unfollowUsers(username, target_username);
            return ResponseEntity.ok().body("Deleted");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Not found");
        }
    }
}
