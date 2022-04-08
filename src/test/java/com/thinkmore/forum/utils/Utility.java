package com.thinkmore.forum.utils;

import com.thinkmore.forum.dto.roles.RolesGetDto;
import com.thinkmore.forum.entity.Roles;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class Utility {

    public RolesGetDto buildRolesGetDto (UUID id, String roleName, String permission) {
        RolesGetDto rolesGetDto = new RolesGetDto();
        rolesGetDto.setId(id);
        rolesGetDto.setRoleName(roleName);
        rolesGetDto.setPermission(permission);
        return rolesGetDto;
    }

    public Roles buildRoles (String roleName, String permission) {
        Roles roles = new Roles();
        roles.setRoleName(roleName);
        roles.setPermission(permission);
        return roles;
    }
}
