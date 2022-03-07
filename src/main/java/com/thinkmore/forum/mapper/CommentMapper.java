package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.comment.CommentGetDto;
import com.thinkmore.forum.dto.comment.CommentMiniGetDto;
import com.thinkmore.forum.dto.comment.CommentPostDto;
import com.thinkmore.forum.dto.comment.CommentPutDto;
import com.thinkmore.forum.entity.Comment;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CommentMapper {
    Comment toEntity(CommentPostDto commentPostDto);

    CommentGetDto fromEntity(Comment comment);

    CommentMiniGetDto entityToMiniDto(Comment comment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copy(CommentPutDto commentPutDto, @MappingTarget Comment comment);
}
