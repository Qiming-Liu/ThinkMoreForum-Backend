package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.FollowUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TestRepository extends JpaRepository<FollowUser, UUID> {

    List<FollowUser> findAllByUsersId(UUID userId);
}
