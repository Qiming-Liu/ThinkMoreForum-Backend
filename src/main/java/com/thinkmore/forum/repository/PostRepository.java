package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID>, JpaSpecificationExecutor<Post> {

    @Override
    void deleteById(UUID uuid);

    List<Post> findByCategory_Title(String title, Pageable pageable);


    long countByCategory_Title(String title);

}