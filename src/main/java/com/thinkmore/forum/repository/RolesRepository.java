package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RolesRepository  extends JpaRepository<Roles, UUID> {

    Optional<Roles> findByRoleName(String roleName);
}
