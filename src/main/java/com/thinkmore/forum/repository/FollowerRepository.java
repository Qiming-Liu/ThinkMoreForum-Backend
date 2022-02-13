package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.FollowUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FollowerRepository extends JpaRepository<FollowUser, UUID> {

    List<FollowUser> findAllByUsersId(UUID userId);

    List<FollowUser> findAllByFollowedUsersId(UUID FollowedUsersId);

    List<FollowUser> findByUsersIdAndFollowedUsersId(UUID userId, UUID FollowedUsersId);

    FollowUser save(FollowUser followUser);

    void deleteByUsersIdAndFollowedUsersId(UUID userId, UUID FollowedUsersId);

}
