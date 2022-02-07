package com.thinkmore.forum.dto.roles;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
public class RolesGetDto implements Serializable {
    private UUID id;
    private String roleName;
    private String permission;
}
