package com.thinkmore.forum.repository;

import com.thinkmore.forum.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID>, JpaSpecificationExecutor<Notification> {

    List<Notification> findByNotifyUsers_IdOrderByCreateTimestampDesc(UUID id);
}