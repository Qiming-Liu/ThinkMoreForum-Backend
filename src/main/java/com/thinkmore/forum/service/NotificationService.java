package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.notification.NotificationGetDto;
import com.thinkmore.forum.mapper.NotificationMapper;
import com.thinkmore.forum.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    public List<NotificationGetDto> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(notificationMapper::fromEntity)
                .collect(Collectors.toList());
    }

    public NotificationGetDto getNotificationById(UUID notificationId, UUID userId) {
        return  notificationMapper.fromEntity(notificationRepository.findByIdAndUsers_Id(notificationId, userId).get());
    }

    public List<NotificationGetDto> getNotificationsByUserId(UUID userId) {
        return notificationRepository.findByUsers_IdOrderByCreateTimestampDesc(userId).stream()
                .map(notificationMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public String userDeleteNotification(UUID notificationId, UUID userId) {
        return notificationRepository.deleteByIdAndUsers_Id(notificationId, userId) > 0?
                "Successfully deleted!":"Deletion failed!";
    }
}
