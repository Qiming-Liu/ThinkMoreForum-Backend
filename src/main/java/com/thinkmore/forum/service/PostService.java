package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.post.PostGetDto;
import com.thinkmore.forum.mapper.PostMapper;
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
                .map(postMapper::fromEntity)
                .collect(Collectors.toList());
    }
}
