package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.FollowPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FollowPostRepository extends JpaRepository<FollowPost, UUID>, JpaSpecificationExecutor<FollowPost> {

    @Modifying
    long deleteByUsers_IdAndPost_Id(UUID id, UUID id1);

    List<FollowPost> findByUsers_IdOrderByCreateTimestampDesc(UUID id);

    Optional<FollowPost> findByPost_IdAndUsers_Id(UUID id, UUID id1);

    long countByPost_Id(UUID id);
}
