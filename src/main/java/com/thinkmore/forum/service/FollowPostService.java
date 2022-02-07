package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.followPost.FollowPostGetDto;
import com.thinkmore.forum.mapper.FollowPostMapper;
import com.thinkmore.forum.repository.FollowPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowPostService {
    private final FollowPostRepository followPostRepo;

    private final FollowPostMapper followPostMapper;

    public List<FollowPostGetDto> getAllFollowPosts() {
        return followPostRepo.findAll().stream()
                .map(followPost -> followPostMapper.fromEntity(followPost))
                .collect(Collectors.toList());
    }

}
