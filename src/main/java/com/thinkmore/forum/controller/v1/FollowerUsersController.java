package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.followerUsers.FollowerUsersGetDto;
import com.thinkmore.forum.service.FollowerUsersService;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/users")
@RequiredArgsConstructor
public class FollowerUsersController {
    private final FollowerUsersService followerUsersService;

    @GetMapping(path = "/followed_status/{username}")
    public ResponseEntity<Boolean> checkFollowedStatus(@PathVariable("username") String username) {
        UUID usersId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(followerUsersService.followStatus(username, usersId));
    }

    @PostMapping(path = "/follow/{username}")
    public ResponseEntity<FollowerUsersGetDto> follow_user(@PathVariable("username") String username) {
        UUID usersId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(followerUsersService.followUsers(usersId, username));
    }

    @DeleteMapping(path = "/unfollow/{username}")
    public ResponseEntity<?> unfollow_user(@PathVariable("username") String target_username) {
        UUID usersId = UUID.fromString(Util.getJwtContext().get(0));
        try {
            followerUsersService.unfollowUsers(usersId, target_username);
            return ResponseEntity.ok().body("Deleted");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Not found");
        }
    }
}
