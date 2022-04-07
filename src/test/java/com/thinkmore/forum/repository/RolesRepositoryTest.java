package com.thinkmore.forum.repository;

import com.thinkmore.forum.ForumApplication;
import com.thinkmore.forum.entity.Roles;
import com.thinkmore.forum.utils.Utility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ForumApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RolesRepositoryTest {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private Utility utility;

    @Test
    public void shouldReturnRoleGivenRoleName() {
        UUID id = UUID.randomUUID();
        Roles role = utility.buildRoles(id, id.toString().substring(0, 10), "permission");
        rolesRepository.save(role);

        Roles newRole = rolesRepository.findByRoleName(id.toString().substring(0, 10)).get();
        assertNotNull(newRole);
    }
}