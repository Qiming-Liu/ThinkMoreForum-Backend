package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID>, JpaSpecificationExecutor<Notification> {

    @Override
    Optional<Notification> findById(UUID uuid);

    List<Notification> findAll();

    List<Notification> findByUsers_IdOrderByCreateTimestampDesc(UUID id);




}