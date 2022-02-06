package com.thinkmore.forum.mapper;

import com.thinkmore.forum.dto.roles.RolesGetDto;
import com.thinkmore.forum.dto.roles.RolesPostDto;
import com.thinkmore.forum.dto.roles.RolesPutDto;
import com.thinkmore.forum.entity.Roles;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface RolesMapper {
    Roles toEntity(RolesPostDto rolesPostDto);

    RolesGetDto fromEntity(Roles roles);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void copy(RolesPutDto rolesPutDto, @MappingTarget Roles roles);
}
