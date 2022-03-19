package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.post.PostGetDto;
import com.thinkmore.forum.dto.post.PostMiniGetDto;
import com.thinkmore.forum.dto.post.PostPostDto;
import com.thinkmore.forum.dto.post.PostPutDto;
import com.thinkmore.forum.entity.Post;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", uses = {CategoryMapper.class})
public interface PostMapper {
    Post toEntity(PostPostDto postPostDto);

    PostGetDto fromEntity(Post post);

    PostMiniGetDto entityToMiniDto(Post post);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copy(PostPutDto postPutDto, @MappingTarget Post post);
}
