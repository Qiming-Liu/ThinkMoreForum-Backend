package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.followPost.FollowPostGetDto;
import com.thinkmore.forum.dto.followPost.FollowPostPostDto;
import com.thinkmore.forum.dto.post.PostMiniGetDto;
import com.thinkmore.forum.dto.users.UsersMiniGetDto;
import com.thinkmore.forum.entity.FollowPost;
import com.thinkmore.forum.mapper.FollowPostMapper;
import com.thinkmore.forum.mapper.PostMapper;
import com.thinkmore.forum.mapper.UsersMapper;
import com.thinkmore.forum.repository.FollowPostRepository;
import com.thinkmore.forum.repository.PostRepository;
import com.thinkmore.forum.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowPostService {

    private final FollowPostRepository followPostRepo;
    private final FollowPostMapper followPostMapper;

    private final UsersRepository usersRepo;
    private final UsersMapper usersMapper;

    private final PostRepository postRepo;
    private final PostMapper postMapper;


    public List<FollowPostGetDto> getAllFollowPosts() {
        return followPostRepo.findAll().stream()
                .map(followPostMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public List<FollowPostGetDto> getAllFollowPostsByUserId(UUID userId) {
        return followPostRepo.findByUsers_IdOrderByCreateTimestampDesc(userId).stream()
                .map(followPostMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public void postFollowPostToUser(UUID postId, UUID userId) {
        UsersMiniGetDto usersMiniGetDto = usersMapper.entityToMiniDto(usersRepo.findById(userId).get());

        PostMiniGetDto postMiniGetDto = postMapper.entityToMiniDto(postRepo.findById(postId).get());

        FollowPostPostDto followPostPostDto = new FollowPostPostDto();
        followPostPostDto.setUsers(usersMiniGetDto);
        followPostPostDto.setPost(postMiniGetDto);
        followPostPostDto.setCreateTimestamp(OffsetDateTime.now());
        FollowPost followPost = followPostMapper.toEntity(followPostPostDto);
        followPostRepo.save(followPost);
    }

    @Transactional
    public String userUnfollowPost(UUID postId, UUID userId) {
        return followPostRepo.deleteByUsers_IdAndPost_Id(userId, postId) > 0?
                "Successfully unfollowed!":"Unfollow failed or you didn't follow this post";
    }

}
