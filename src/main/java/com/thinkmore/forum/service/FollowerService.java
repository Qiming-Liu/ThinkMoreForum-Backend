package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.follower.FollowerGetDto;
import com.thinkmore.forum.entity.FollowUser;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.exception.UserNotFoundException;
import com.thinkmore.forum.mapper.FollowerMapper;
import com.thinkmore.forum.repository.FollowerRepository;
import com.thinkmore.forum.repository.UsersRepository;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowerService {
    private final FollowerRepository followerRepository;
    private final UsersRepository usersRepository;
    private final FollowerMapper followerMapper;

    public List<FollowerGetDto> getFollowersById(UUID Id) {
        return followerRepository.findAllByUsersId(Id).stream().map(followerMapper::fromEntity).collect(Collectors.toList());
    }

    public List<FollowerGetDto> getFriendsById(UUID Id) {
        return followerRepository.findAllByFollowedUsersId(Id).stream().map(followerMapper::fromEntity).collect(Collectors.toList());
    }

    public FollowerGetDto followUsers(UUID Id) {
        FollowUser followUser = new FollowUser();
        UUID currentId = UUID.fromString(Util.getJwtContext().get(0));
        Users user = usersRepository.findById(currentId)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));
        Users followedUser = usersRepository.findById(Id)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));
        if (followerRepository.findByUsersIdAndFollowedUsersId(Id, currentId).isEmpty()) {
            followUser.setUsers(user);
            followUser.setFollowedUsers(followedUser);
            followUser.setCreateTimestamp(OffsetDateTime.now());

            followerRepository.save(followUser);
        } else {
            System.out.println("You have already followed this user");
        }

        return followerMapper.fromEntity(followUser);
    }

    public void unfollowUsers(UUID userId, UUID FollowedUsersId) {
        followerRepository.deleteByUsersIdAndFollowedUsersId(userId, FollowedUsersId);
    }
}
