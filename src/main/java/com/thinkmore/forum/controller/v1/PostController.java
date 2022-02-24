package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.category.CategoryMiniGetDto;
import com.thinkmore.forum.dto.post.PostGetDto;
import com.thinkmore.forum.dto.post.PostMiniGetDto;
import com.thinkmore.forum.dto.post.PostPostDto;
import com.thinkmore.forum.dto.post.PostPutDto;
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

    @GetMapping(path = "/{post_id}")
    public ResponseEntity<PostGetDto> findPostById(@PathVariable("post_id") String post_id) throws Exception {
        UUID postId = UUID.fromString(post_id);
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping(path="/mini")
    public ResponseEntity<List<PostMiniGetDto>> findAllCoreInfo() {
        return ResponseEntity.ok(postService.getAllPostsCoreInfo());
    }

    @DeleteMapping(path = "/{post_id}")
    public ResponseEntity<String> deletePostById(@PathVariable("post_id") String post_id){
        UUID postId = UUID.fromString(post_id);
        postService.deletePostById(postId);
        return ResponseEntity.ok(String.format("Successfully deleted the post with id %s", post_id));
    }

    @PostMapping
    public ResponseEntity<String> postPost(@RequestBody PostPostDto postPostDto) {
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        String response = postService.userPostPost(userId, postPostDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<String> editPost(@RequestBody PostPutDto postPutDto) {
        String response = postService.userEditPost(postPutDto);
        return ResponseEntity.ok(response);
    }
}
