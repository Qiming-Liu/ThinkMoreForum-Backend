package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.followPost.FollowPostGetDto;
import com.thinkmore.forum.dto.followPost.FollowPostPostDto;
import com.thinkmore.forum.dto.post.PostMiniGetDto;
import com.thinkmore.forum.dto.users.UsersMiniGetDto;
import com.thinkmore.forum.entity.FollowPost;
import com.thinkmore.forum.entity.Post;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.exception.UserNotFoundException;
import com.thinkmore.forum.mapper.FollowPostMapper;
import com.thinkmore.forum.mapper.PostMapper;
import com.thinkmore.forum.mapper.UsersMapper;
import com.thinkmore.forum.repository.FollowPostRepository;
import com.thinkmore.forum.repository.PostRepository;
import com.thinkmore.forum.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowPostService {

    private final FollowPostRepository followPostRepository;
    private final FollowPostMapper followPostMapper;

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    private final NotificationService notificationService;

    public List<FollowPostGetDto> getAllFollowPostsByUserId(UUID userId) {
        return followPostRepository.findByUsers_IdOrderByCreateTimestampDesc(userId).stream()
                .map(followPostMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public List<FollowPostGetDto> getAllFollowPostsByUsername(String username) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserName"));
        return followPostRepository.findByUsers_IdOrderByCreateTimestampDesc(user.getId()).stream()
                .map(followPostMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public Boolean checkUserFollowingState(UUID postId, UUID userId) {
        Optional<FollowPost> targetFollowPost = followPostRepository.findByPost_IdAndUsers_Id(postId, userId);
        if (targetFollowPost.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void postFollowPostToUser(UUID postId, UUID userId) {
        Users users = usersRepository.findById(userId).get();
        UsersMiniGetDto usersMiniGetDto = usersMapper.entityToMiniDto(users);
        Post post = postRepository.findById(postId).get();
        PostMiniGetDto postMiniGetDto = postMapper.entityToMiniDto(post);

        FollowPostPostDto followPostPostDto = new FollowPostPostDto();
        followPostPostDto.setUsers(usersMiniGetDto);
        followPostPostDto.setPost(postMiniGetDto);
        FollowPost followPost = followPostMapper.toEntity(followPostPostDto);
        followPostRepository.save(followPost);

        notificationService.postNotification(users, post.getPostUsers(), " followed your post.");

        Post postToUpdate = postRepository.findById(postId).get();
        int newFollowCount = (int) followPostRepository.countByPost_Id(postId);
        postToUpdate.setFollowCount(newFollowCount);
        postRepository.save(postToUpdate);

    }

    @Transactional
    public String userUnfollowPost(UUID postId, UUID userId) {
        long responseValue = followPostRepository.deleteByUsers_IdAndPost_Id(userId, postId);
        if (responseValue > 0) {
            Post postToUpdate = postRepository.findById(postId).get();
            int newFollowCount = (int) followPostRepository.countByPost_Id(postId);
            postToUpdate.setFollowCount(newFollowCount);
            postRepository.save(postToUpdate);
            return "Successfully unfollowed!";
        }
        return "Unfollow failed or you didn't follow this post";
    }

}
