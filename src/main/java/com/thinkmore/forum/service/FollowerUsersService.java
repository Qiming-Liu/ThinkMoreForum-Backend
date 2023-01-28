package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.followerUsers.FollowerUsersGetDto;
import com.thinkmore.forum.entity.FollowUser;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.exception.UserNotFoundException;
import com.thinkmore.forum.mapper.FollowerUsersMapper;
import com.thinkmore.forum.repository.FollowerUsersRepository;
import com.thinkmore.forum.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowerUsersService {
    private final FollowerUsersRepository followerUsersRepository;
    private final UsersRepository usersRepository;
    private final FollowerUsersMapper followerUsersMapper;
    private final NotificationService notificationService;

    public List<FollowerUsersGetDto> getFollowersByUsername(String username) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserName"));
        return followerUsersRepository.findAllByFollowedUsersId(user.getId()).stream().map(followerUsersMapper::fromEntity).collect(Collectors.toList());
    }

    public List<FollowerUsersGetDto> getFollowingByUsername(String username) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserName"));
        return followerUsersRepository.findAllByUsersId(user.getId()).stream().map(followerUsersMapper::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    public FollowerUsersGetDto followUsers(UUID myUsersId, String hisUsername) {
        Users myUser = usersRepository.findById(myUsersId)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));
        Users hisUser = usersRepository.findByUsername(hisUsername)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));

        FollowUser followUser = new FollowUser();
        followUser.setUsers(myUser);
        followUser.setFollowedUsers(hisUser);
        followerUsersRepository.save(followUser);
        notificationService.postNotification(myUser, hisUser, " followed you.");

        return followerUsersMapper.fromEntity(followUser);
    }

    @Transactional
    public void unfollowUsers(UUID userID, String followedUsername) {
        Users followedUser = usersRepository.findByUsername(followedUsername)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserName"));
        followerUsersRepository.deleteByUsersIdAndFollowedUsersId(userID, followedUser.getId());
    }

    public boolean followStatus(String username, UUID usersId) {
        Users tampUser = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserName"));

        boolean status;
        status = !followerUsersRepository.findByUsersIdAndFollowedUsersId(usersId, tampUser.getId()).isEmpty();
        return status;
    }
}
