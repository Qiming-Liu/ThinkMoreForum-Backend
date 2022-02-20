package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID>, JpaSpecificationExecutor<Notification> {

    List<Notification> findByUsers_IdOrderByCreateTimestampDesc(UUID id);

    Optional<Notification> findByIdAndUsers_Id(UUID id, UUID id1);

    @Modifying
    long deleteByIdAndUsers_Id(UUID id, UUID id1);
}