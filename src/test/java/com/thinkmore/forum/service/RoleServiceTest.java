package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.roles.RolesGetDto;
import com.thinkmore.forum.entity.Roles;
import com.thinkmore.forum.mapper.RolesMapper;
import com.thinkmore.forum.repository.RolesRepository;
import com.thinkmore.forum.repository.UsersRepository;
import com.thinkmore.forum.utils.Utility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {Utility.class})
class RoleServiceTest {
    @Mock
    private RolesRepository rolesRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private RolesMapper rolesMapper;

    @Autowired
    private Utility utility;

    RoleService roleService;

    @BeforeEach
    void setup() {
        roleService = new RoleService(rolesRepository, rolesMapper, usersRepository);
    }

    @Test
    public void shouldReturnRoleListGivenAccountsExist() {
        Roles role1 = utility.buildRoles("roleName", "permission");
        Roles role2 = utility.buildRoles("roleName", "permission");
        when(rolesRepository.findAll()).thenReturn(List.of(role1, role2));
        List<RolesGetDto> returnedRoleList = roleService.getAllRoles();
        assertNotNull(returnedRoleList);
        assertEquals(2, returnedRoleList.size());
    }

}