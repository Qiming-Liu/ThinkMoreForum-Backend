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

    @GetMapping(path = "/findAll/{post_id}")
    public ResponseEntity<List<FollowPostGetDto>> findAll() {
        List<FollowPostGetDto> followPostList = followPostService.getAllFollowPosts();
        return ResponseEntity.ok(followPostList);
    }

    @GetMapping(path = "/findAllByUserId/{post_id}")
    public ResponseEntity<List<FollowPostGetDto>> findAllByUserId() {
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        List<FollowPostGetDto> followPostList = followPostService.getAllFollowPostsByUserId(userId);
        return ResponseEntity.ok(followPostList);
    }

    @GetMapping(path = "/findAllByUsername/{username}")
    public ResponseEntity<List<FollowPostGetDto>> getFollowPostByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(followPostService.getAllFollowPostsByUsername(username));
    }

    @GetMapping(path="/checkUserFollowingState/{post_id}")
    public ResponseEntity<Boolean> checkUserFollowingState(@PathVariable("post_id") String post_id) {
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        UUID postId = UUID.fromString(post_id);
        Boolean userIsFollowingPost = followPostService.checkUserFollowingState(postId, userId);
        return ResponseEntity.ok(userIsFollowingPost);
    }

    @PostMapping(path = "/userFollowPost/{post_id}")
    public ResponseEntity<String> userFollowPost(@PathVariable("post_id") String post_id) {
        UUID postId = UUID.fromString(post_id);
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        followPostService.postFollowPostToUser(postId, userId);
        return ResponseEntity.ok(String.format("successfully followed post with id %s", post_id));
    }

    @DeleteMapping(path = "/userUnfollowPost/{post_id}")
    public ResponseEntity<String> userUnfollowPost(@PathVariable("post_id") String post_id) {
        UUID postId = UUID.fromString(post_id);
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(followPostService.userUnfollowPost(postId, userId));
    }
}
