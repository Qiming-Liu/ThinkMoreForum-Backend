package com.thinkmore.forum.controller.v1;

import com.thinkmore.forum.dto.comment.CommentPostDto;
import com.thinkmore.forum.service.CommentService;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<String> postComment(@RequestBody CommentPostDto commentDto) {
        Util.checkPermission("postComment");
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(commentService.postComment(userId, commentDto));
    }
}
