package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.category.CategoryGetDto;
import com.thinkmore.forum.dto.category.CategoryPostDto;
import com.thinkmore.forum.dto.category.CategoryPutDto;
import com.thinkmore.forum.dto.followPost.FollowPostGetDto;
import com.thinkmore.forum.dto.followPost.FollowPostPostDto;
import com.thinkmore.forum.dto.followPost.FollowPostPutDto;
import com.thinkmore.forum.entity.Category;
import com.thinkmore.forum.entity.FollowPost;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {PostMapper.class})
public interface FollowPostMapper {
    FollowPost toEntity(FollowPostPostDto followPostPostDto);

    FollowPostGetDto fromEntity(FollowPost followPost);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copy(FollowPostPutDto followPostPutDto, @MappingTarget FollowPost followPost);
}
