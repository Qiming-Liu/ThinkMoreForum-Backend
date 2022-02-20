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
    @GetMapping(path = "/follower/{user_id}/{followed_users_id}")
    public ResponseEntity<List<FollowerGetDto>> view_follower(@PathVariable("user_id") UUID current_UserId, @PathVariable("followed_users_id") UUID target_UserId) {
        return ResponseEntity.ok(followerService.getFollowersById(target_UserId));
    }

    @GetMapping(path = "/followed/{user_id}/{followed_users_id}")
    public ResponseEntity<List<FollowerGetDto>> view_followed_user(@PathVariable("user_id") UUID current_UserId, @PathVariable("followed_users_id") UUID target_UserId) {
        return ResponseEntity.ok(followerService.getFriendsById(target_UserId));
    }

    @PostMapping(path = "/follow/{user_id}")
    public ResponseEntity<FollowerGetDto> follow_user(@PathVariable("user_id") UUID target_UserId) {
        return ResponseEntity.ok(followerService.followUsers(target_UserId));
    }

    @DeleteMapping(path = "/follow/{user_id}")
    public ResponseEntity<?> unfollow_user(@PathVariable("user_id") UUID target_UserId) {
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        try {
            followerService.unfollowUsers(userId, target_UserId);
            return ResponseEntity.ok().body("Deleted");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Not found");
        }
    }
}
