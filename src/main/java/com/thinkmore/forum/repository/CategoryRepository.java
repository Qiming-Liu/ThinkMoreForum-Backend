package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Category;
import com.thinkmore.forum.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>, JpaSpecificationExecutor<Category> {

    static Optional<Category> findByTitle(String title) {
        return null;
    }

    Category save(Category category);

}