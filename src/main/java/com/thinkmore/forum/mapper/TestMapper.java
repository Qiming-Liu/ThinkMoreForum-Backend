package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.test.TestGetDto;
import com.thinkmore.forum.entity.FollowUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TestMapper {
    TestGetDto fromEntity(FollowUser followUser);
}
