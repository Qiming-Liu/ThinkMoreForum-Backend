package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.follower.FollowerGetDto;
import com.thinkmore.forum.mapper.FollowerMapper;
import com.thinkmore.forum.repository.FollowerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowerService {
    private final FollowerRepository followerRepository;

    private final FollowerMapper followerMapper;

    public List<FollowerGetDto> getFollowersById(UUID Id) {
        return followerRepository.findAllByUsersId(Id).stream().map(followUser -> followerMapper.fromEntity(followUser)).collect(Collectors.toList());
    }

}
