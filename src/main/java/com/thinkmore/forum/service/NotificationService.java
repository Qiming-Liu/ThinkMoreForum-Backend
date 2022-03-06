package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.notification.NotificationGetDto;
import com.thinkmore.forum.entity.Notification;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.mapper.NotificationMapper;
import com.thinkmore.forum.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.OffsetDateTimeType;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Transactional
    public List<NotificationGetDto> getNotificationsByUserId(UUID userId) {

        List<NotificationGetDto> all = notificationRepository.findByUsers_IdOrderByCreateTimestampDesc(userId).stream()
                .map(notificationMapper::fromEntity)
                .collect(Collectors.toList());

        // remove viewed notification
        all.removeIf(NotificationGetDto::getViewed);

        return all;
    }

    @Transactional
    public boolean markAsViewed(UUID notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setViewed(true);
            notificationRepository.save(notification);
        });
        return true;
    }

    @Transactional
    public boolean markAllAsViewed(UUID userId) {
        notificationRepository.findByUsers_IdOrderByCreateTimestampDesc(userId)
                .forEach(notification -> {
                    notification.setViewed(true);
                    notificationRepository.save(notification);
                });

        return true;
    }

    @Transactional
    public void postNotification(Users notifyUser, Users triggerUser, String context) {

        Notification notification = new Notification();
        notification.setUsers(notifyUser);
        notification.setContext(triggerUser.getUsername() + context);
        notification.setImgUrl(triggerUser.getProfileImgUrl());
        notification.setViewed(false);
        notificationRepository.save(notification);
    }
}
