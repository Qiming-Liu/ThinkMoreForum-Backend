package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.Comment.CommentGetDto;
import com.thinkmore.forum.dto.Comment.CommentPostDto;
import com.thinkmore.forum.dto.Comment.CommentPutDto;
import com.thinkmore.forum.service.CommentService;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping(path = "/{comment_id}")
    public ResponseEntity<CommentGetDto> findPostCommentById(@PathVariable("comment_id") String comment_id) throws Exception {
        UUID commentId = UUID.fromString(comment_id);
        return ResponseEntity.ok(commentService.getPostCommentById(commentId));
    }

    @GetMapping
    public ResponseEntity<List<CommentGetDto>> findPostCommentsByPost(@RequestParam String post_id) {
        UUID postId = UUID.fromString(post_id);
        return ResponseEntity.ok(commentService.getAllByPost(postId));
    }

    @PostMapping
    public ResponseEntity<String> postCommentToPost(@RequestBody CommentPostDto commentDto) {
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        String response = commentService.userPostComment(userId, commentDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{comment_id}")
    public ResponseEntity<String> deleteCommentById(@PathVariable("comment_id") String comment_id){
        UUID commentId = UUID.fromString(comment_id);
        commentService.deleteCommentById(commentId);
        return ResponseEntity.ok(String.format("Successfully deleted the post with id %s", comment_id));
    }

    @PutMapping
    public ResponseEntity<String> editComment(@RequestBody CommentPutDto commentPutDto) {
        String response = commentService.userEditComment(commentPutDto);
        return ResponseEntity.ok(response);
    }
}
