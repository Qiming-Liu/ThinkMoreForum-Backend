package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.notification.NotificationGetDto;
import com.thinkmore.forum.dto.post.PostGetDto;
import com.thinkmore.forum.mapper.NotificationMapper;
import com.thinkmore.forum.mapper.PostMapper;
import com.thinkmore.forum.repository.NotificationRepository;
import com.thinkmore.forum.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepo;

    private final PostMapper postMapper;

    public List<PostGetDto> getAllPosts() {

        return postRepo.findAll().stream()
                .map(post -> postMapper.fromEntity(post))
                .collect(Collectors.toList());
    }
}
