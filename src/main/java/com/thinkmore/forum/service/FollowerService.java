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

    public List<FollowerGetDto> getFollowersByUsername(String username) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserName"));
        return followerRepository.findAllByUsersId(user.getId()).stream().map(followerMapper::fromEntity).collect(Collectors.toList());
    }

    public List<FollowerGetDto> getFriendsByUsername(String username) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserName"));
        return followerRepository.findAllByFollowedUsersId(user.getId()).stream().map(followerMapper::fromEntity).collect(Collectors.toList());
    }

    public FollowerGetDto followUsers(String username) {
        Users tampUser = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserName"));
        FollowUser followUser = new FollowUser();
        UUID currentId = UUID.fromString(Util.getJwtContext().get(0));
        Users user = usersRepository.findById(currentId)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));
        Users followedUser = usersRepository.findById(tampUser.getId())
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));
        if (followerRepository.findByUsersIdAndFollowedUsersId(currentId, tampUser.getId()).isEmpty()) {
            followUser.setUsers(user);
            followUser.setFollowedUsers(followedUser);
            followUser.setCreateTimestamp(OffsetDateTime.now());

            followerRepository.save(followUser);
        } else {
            System.out.println("You have already followed this user");
            return null;
        }

        return followerMapper.fromEntity(followUser);
    }

    public void unfollowUsers(String username, String followedUsername) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserName"));
        Users followedUser = usersRepository.findByUsername(followedUsername)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserName"));
        followerRepository.deleteByUsersIdAndFollowedUsersId(user.getId(), followedUser.getId());
    }

    public boolean followStatus(String username) {
        Users tampUser = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserName"));
        UUID currentId = UUID.fromString(Util.getJwtContext().get(0));
        boolean status;
        if (followerRepository.findByUsersIdAndFollowedUsersId(currentId, tampUser.getId()).isEmpty()) {
            status = false;
        } else {
            status = true;
        }
        return status;
    }
}
