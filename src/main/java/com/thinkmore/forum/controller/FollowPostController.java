package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.followPost.FollowPostGetDto;
import com.thinkmore.forum.dto.img.ImgGetDto;
import com.thinkmore.forum.service.FollowPostService;
import com.thinkmore.forum.service.ImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
