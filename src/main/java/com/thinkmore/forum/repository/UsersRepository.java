package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Roles;
import com.thinkmore.forum.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsersRepository  extends JpaRepository<Users, UUID> {

    Optional<Users> findByUsername(String username);

    //get user by username containing string return list of user
    List<Users> findByUsernameContainingIgnoreCase(String username);

    Optional<Users> findByEmail(String email);
    List<Users> findByRole(Roles role);
}
