package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.post.PostPostDto;
import com.thinkmore.forum.service.PostService;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> postPost(@RequestBody PostPostDto postPostDto) {
        Util.checkPermission("makePost");
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        String response = postService.postPost(userId, postPostDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{post_id}/visibility")
    public ResponseEntity<Boolean> changePostVisibility(@PathVariable("post_id") String post_id) {
        Util.checkPermission("postManagement");
        UUID postId = UUID.fromString(post_id);
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        Boolean response = postService.changePostVisibility(postId, userId);
        return ResponseEntity.ok(response);
    }
}
