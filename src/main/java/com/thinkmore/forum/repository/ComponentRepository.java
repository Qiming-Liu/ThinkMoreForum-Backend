package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ComponentRepository extends JpaRepository<Component, UUID>, JpaSpecificationExecutor<Component> {

    Optional<Component> findByName(String title);
}