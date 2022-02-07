package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.followPost.FollowPostGetDto;
import com.thinkmore.forum.service.FollowPostService;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/watching_post")
@RequiredArgsConstructor
public class FollowPostController {

    private final FollowPostService followPostService;

    @GetMapping
    public ResponseEntity<List<FollowPostGetDto>> findAll() {
        List<FollowPostGetDto> followPostList = followPostService.getAllFollowPosts();
        return ResponseEntity.ok(followPostList);
    }

    @PostMapping
    public ResponseEntity<String> userFollowPost(@RequestParam String post_id) {
        UUID postId = UUID.fromString(post_id);
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        followPostService.postFollowPostToUser(postId, userId);
        return ResponseEntity.ok(String.format("successfully followed post with id %s", post_id));
    }

    @DeleteMapping
    public ResponseEntity<String> userUnfollowPost(@RequestParam String post_id) {
        UUID postId = UUID.fromString(post_id);
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(followPostService.userUnfollowPost(postId, userId));
    }

}
