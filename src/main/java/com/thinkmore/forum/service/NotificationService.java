package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.notification.NotificationGetDto;
import com.thinkmore.forum.entity.Notification;
import com.thinkmore.forum.mapper.NotificationMapper;
import com.thinkmore.forum.repository.NotificationRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepo;

    private final NotificationMapper notificationMapper;

    public List<NotificationGetDto> getAllNotifications() {

        return notificationRepo.findAll().stream()
                .map(notification -> notificationMapper.fromEntity(notification))
                .collect(Collectors.toList());
    }

    public NotificationGetDto getNotificationById(UUID notificationId) {

        return  notificationMapper.fromEntity(notificationRepo.findById(notificationId).get());
    }
}
