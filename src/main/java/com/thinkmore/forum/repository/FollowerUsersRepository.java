package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.FollowUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FollowerUsersRepository extends JpaRepository<FollowUser, UUID> {

    List<FollowUser> findAllByUsersId(UUID userId);

    List<FollowUser> findAllByFollowedUsersId(UUID FollowedUsersId);

    List<FollowUser> findByUsersIdAndFollowedUsersId(UUID userId, UUID FollowedUsersId);

    long deleteByUsersIdAndFollowedUsersId(UUID userId, UUID FollowedUsersId);

}
