package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.oauth.OauthGetDto;
import com.thinkmore.forum.dto.oauth.OauthPostDto;
import com.thinkmore.forum.dto.oauth.OauthPutDto;
import com.thinkmore.forum.entity.Oauth;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface OauthMapper {
    Oauth toEntity(OauthPostDto oauthPostDto);

    OauthGetDto fromEntity(Oauth oauth);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copy(OauthPutDto oauthPutDto, @MappingTarget Oauth oauth);
}
