package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.FollowPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface FollowPostRepository extends JpaRepository<FollowPost, UUID>, JpaSpecificationExecutor<FollowPost> {
}