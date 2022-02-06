package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.post.PostGetDto;
import com.thinkmore.forum.entity.Post;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PostMapper {
    Post toEntity(PostGetDto postGetDto);

    PostGetDto fromEntity(Post post);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copy(PostGetDto postGetDto, @MappingTarget Post post);
}
