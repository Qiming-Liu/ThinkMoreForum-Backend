package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.follower.FollowerGetDto;
import com.thinkmore.forum.dto.follower.FollowerPostDto;
import com.thinkmore.forum.entity.FollowUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FollowerMapper {
    FollowerGetDto fromEntity(FollowUser followUser);

    FollowUser toEntity(FollowerPostDto followerPostDto);
}
