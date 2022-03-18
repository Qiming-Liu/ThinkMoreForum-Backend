package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.followPost.FollowPostGetDto;
import com.thinkmore.forum.service.FollowPostService;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/post/follows")
@RequiredArgsConstructor
public class FollowPostController {
    private final FollowPostService followPostService;

    @GetMapping(path = "/find_all_by_user_id")
    public ResponseEntity<List<FollowPostGetDto>> findAllByUserId() {
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        List<FollowPostGetDto> followPostList = followPostService.getAllFollowPostsByUserId(userId);
        return ResponseEntity.ok(followPostList);
    }

    @GetMapping(path="/check_user_following_state/{post_id}")
    public ResponseEntity<Boolean> checkUserFollowingState(@PathVariable String post_id) {
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        UUID postId = UUID.fromString(post_id);
        Boolean userIsFollowingPost = followPostService.checkUserFollowingState(postId, userId);
        return ResponseEntity.ok(userIsFollowingPost);
    }

    @PostMapping(path = "/user_follow_post/{post_id}")
    public ResponseEntity<String> userFollowPost(@PathVariable String post_id) {
        UUID postId = UUID.fromString(post_id);
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        followPostService.postFollowPostToUser(postId, userId);
        return ResponseEntity.ok(String.format("successfully followed post with id %s", post_id));
    }

    @DeleteMapping(path = "/user_unfollow_post/{post_id}")
    public ResponseEntity<String> userUnfollowPost(@PathVariable String post_id) {
        UUID postId = UUID.fromString(post_id);
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(followPostService.userUnfollowPost(postId, userId));
    }
}
