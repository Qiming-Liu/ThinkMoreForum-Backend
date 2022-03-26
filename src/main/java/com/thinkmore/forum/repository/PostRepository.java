package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Post;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID>, JpaSpecificationExecutor<Post> {

    @Override
    void deleteById(@NotNull UUID uuid);

    //find post by title
    List<Post> findByTitle(String title);

    List<Post> findByTitleContainingIgnoreCase(String title);

    //findByTitleContainingString


    List<Post> findByCategory_Title(String title);

    List<Post> findByCategory_Title(String title, Pageable pageable);

    List<Post> findByPostUsersId(UUID Id);

    long countByCategory_Title(String title);

    long countByCategory_IdAndVisibilityIsTrue(UUID id);

    List<Post> findByCategory_IdAndVisibilityIsTrue(UUID id, Pageable pageable);

}