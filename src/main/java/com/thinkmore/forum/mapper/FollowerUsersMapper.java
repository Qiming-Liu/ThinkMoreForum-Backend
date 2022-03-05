package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.followerUsers.FollowerUsersGetDto;
import com.thinkmore.forum.dto.followerUsers.FollowerUsersPostDto;
import com.thinkmore.forum.entity.FollowUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FollowerUsersMapper {
    FollowerUsersGetDto fromEntity(FollowUser followUser);

    FollowUser toEntity(FollowerUsersPostDto followerUsersPostDto);
}
