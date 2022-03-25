package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>, JpaSpecificationExecutor<Category> {

    @Override
    Optional<Category> findById(UUID uuid);

    @Override
    void deleteById(UUID uuid);

    Optional<Category> findByTitle(String title);

    List<Category> findByOrderBySortOrderAsc();
}