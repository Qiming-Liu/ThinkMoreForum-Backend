package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.post.PostGetDto;
import com.thinkmore.forum.dto.post.PostMiniGetDto;
import com.thinkmore.forum.service.PostService;
import com.thinkmore.forum.util.Util;
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

    @GetMapping(path = "/mini")
    public ResponseEntity<List<PostMiniGetDto>> findAllCoreInfo() {
        return ResponseEntity.ok(postService.getAllPostsCoreInfo());
    }

    @GetMapping(path = "/user/{username}")
    public ResponseEntity<List<PostGetDto>> findPostByPostUsersId(@PathVariable String username) {
        return ResponseEntity.ok(postService.getPostsByPostUsersName(username));
    }

    @PostMapping
    public ResponseEntity<String> postPost(@RequestParam String categoryTitle, @RequestParam String title, @RequestParam String context, @RequestParam String headImgUrl) {
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        String response = postService.postPost(userId, categoryTitle, title, context, headImgUrl);
        return ResponseEntity.ok(response);
    }

    @PutMapping(path = "/{post_id}/visibility")
    public ResponseEntity<Boolean> changePostVisibility(@PathVariable("post_id") String post_id) {
        UUID postId = UUID.fromString(post_id);
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        Boolean response = postService.changePostVisibility(postId, userId);
        return ResponseEntity.ok(response);
    }
}
