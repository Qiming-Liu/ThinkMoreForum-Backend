package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID>, JpaSpecificationExecutor<Post> {

    @Override
    void deleteById(UUID uuid);
}