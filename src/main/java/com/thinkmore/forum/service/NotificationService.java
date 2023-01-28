package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.notification.NotificationGetDto;
import com.thinkmore.forum.entity.Notification;
import com.thinkmore.forum.entity.Users;
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

    public List<NotificationGetDto> getNotificationsByUserId(UUID userId) {

        List<NotificationGetDto> all = notificationRepository.findByNotifyUsers_IdOrderByCreateTimestampDesc(userId).stream()
                .map(notificationMapper::fromEntity)
                .collect(Collectors.toList());

        // remove viewed notification
        all.removeIf(NotificationGetDto::getViewed);

        return all;
    }

    public boolean markAsViewed(UUID notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setViewed(true);
            notificationRepository.save(notification);
        });
        return true;
    }

    public boolean markAllAsViewed(UUID userId) {
        notificationRepository.findByNotifyUsers_IdOrderByCreateTimestampDesc(userId)
                .forEach(notification -> {
                    notification.setViewed(true);
                    notificationRepository.save(notification);
                });

        return true;
    }

    public void postNotification(Users triggerUser, Users notifyUser, String context) {

        Notification notification = new Notification();
        notification.setTriggerUsers(triggerUser);
        notification.setNotifyUsers(notifyUser);
        notification.setContext(triggerUser.getUsername() + context);
        notification.setViewed(false);
        notificationRepository.save(notification);
    }
}
