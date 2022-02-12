package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface PostCommentRepository extends JpaRepository<PostComment, UUID>, JpaSpecificationExecutor<PostComment> {
    List<PostComment> findByPost_IdOrderByCreateTimestampAsc(UUID id);

    @Override
    void deleteById(UUID uuid);
}