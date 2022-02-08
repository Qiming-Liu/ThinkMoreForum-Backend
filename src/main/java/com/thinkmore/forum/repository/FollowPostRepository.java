package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.FollowPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface FollowPostRepository extends JpaRepository<FollowPost, UUID>, JpaSpecificationExecutor<FollowPost> {

    @Modifying
    long deleteByUsers_IdAndPost_Id(UUID id, UUID id1);

    List<FollowPost> findByUsers_IdOrderByCreateTimestampDesc(UUID id);


}