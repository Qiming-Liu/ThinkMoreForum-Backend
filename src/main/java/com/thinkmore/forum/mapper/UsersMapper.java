package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.users.UsersPostDto;
import com.thinkmore.forum.dto.users.UsersPutDto;
import com.thinkmore.forum.entity.Users;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UsersMapper {
    Users toEntity(UsersPostDto usersPostDto);

    UsersPostDto fromEntity(Users users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copy(UsersPutDto usersPutDto, @MappingTarget Users users);
}
