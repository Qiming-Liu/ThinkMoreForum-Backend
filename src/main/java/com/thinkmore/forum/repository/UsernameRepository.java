package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UsernameRepository extends JpaRepository<Users, UUID> {

    @Query("select u from Users u where u.id = :id")
    Optional<Users> findByUsersId(@Param("id") UUID id);


}
