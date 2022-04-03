package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID>, JpaSpecificationExecutor<Comment> {

    List<Comment> findByPost_IdOrderByCreateTimestampAsc(UUID id);

    @Override
    void deleteById(UUID uuid);

    long countByPost_IdAndVisibilityIsTrue(UUID id);

}