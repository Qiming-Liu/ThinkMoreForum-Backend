package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.post.PostGetDto;
import com.thinkmore.forum.dto.post.PostPutDto;
import com.thinkmore.forum.dto.postComment.PostCommentGetDto;
import com.thinkmore.forum.dto.postComment.PostCommentPostDto;
import com.thinkmore.forum.dto.postComment.PostCommentPutDto;
import com.thinkmore.forum.service.PostCommentService;
import com.thinkmore.forum.service.PostService;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/comment")
@RequiredArgsConstructor
public class PostCommentController {

    private final PostCommentService postCommentService;

    @GetMapping(path = "/{comment_id}")
    public ResponseEntity<PostCommentGetDto> findPostCommentById(@PathVariable("comment_id") String comment_id) throws Exception {
        UUID commentId = UUID.fromString(comment_id);
        return ResponseEntity.ok(postCommentService.getPostCommentById(commentId));
    }

    @GetMapping
    public ResponseEntity<List<PostCommentGetDto>> findPostCommentsByPost(@RequestParam String post_id) {
        UUID postId = UUID.fromString(post_id);
        return ResponseEntity.ok(postCommentService.getAllByPost(postId));
    }

    @PostMapping
    public ResponseEntity<String> postCommentToPost(@RequestBody PostCommentPostDto commentDto) {
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        String response = postCommentService.userPostComment(userId, commentDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{comment_id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable("comment_id") String comment_id){
        UUID commentId = UUID.fromString(comment_id);
        postCommentService.deleteCommentById(commentId);
        return ResponseEntity.ok(String.format("Successfully deleted the post with id %s", comment_id));
    }

    @PutMapping
    public ResponseEntity<String> editComment(@RequestBody PostCommentPutDto commentPutDto) {
        String response = postCommentService.userEditComment(commentPutDto);
        return ResponseEntity.ok(response);
    }
}
