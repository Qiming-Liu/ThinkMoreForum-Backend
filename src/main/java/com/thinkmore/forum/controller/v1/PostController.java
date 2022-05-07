package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.post.PostGetDto;
import com.thinkmore.forum.dto.post.PostMiniPutDto;
import com.thinkmore.forum.dto.post.PostPostDto;
import com.thinkmore.forum.service.PostService;
import com.thinkmore.forum.util.Util;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    static final Counter postCounter = Metrics.
            counter("post.counter.total", "controller", "post");

    @GetMapping(path = "/search/{string}")
    public ResponseEntity<List<PostGetDto>> getPostByTitleContainingString(@PathVariable("string") String string) {
        List<PostGetDto> response = postService.getPostByTitleContainingString(string);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<String> postPost(@RequestBody PostPostDto postPostDto) {
        Util.checkPermission("makePost");
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        String response = postService.postPost(userId, postPostDto);
        postCounter.increment(1D);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{post_id}")
    public ResponseEntity<Boolean> editPost(@PathVariable("post_id") String post_id, @RequestBody PostMiniPutDto postMiniPutDto) {
        Util.checkPermission("postManagement");
        UUID postId = UUID.fromString(post_id);
        UUID usersId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(postService.editPost(postId, usersId, postMiniPutDto));
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
