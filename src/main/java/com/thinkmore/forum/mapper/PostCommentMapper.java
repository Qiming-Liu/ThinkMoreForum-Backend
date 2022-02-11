package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.postComment.PostCommentGetDto;
import com.thinkmore.forum.dto.postComment.PostCommentMiniGetDto;
import com.thinkmore.forum.dto.postComment.PostCommentPostDto;
import com.thinkmore.forum.dto.postComment.PostCommentPutDto;
import com.thinkmore.forum.entity.PostComment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PostCommentMapper {
    PostComment toEntity(PostCommentPostDto postCommentPostDto);

    PostCommentGetDto fromEntity(PostComment postComment);

    PostCommentMiniGetDto entityToMiniDto(PostComment postComment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copy(PostCommentPutDto postCommentPutDto, @MappingTarget PostComment postComment);
}
