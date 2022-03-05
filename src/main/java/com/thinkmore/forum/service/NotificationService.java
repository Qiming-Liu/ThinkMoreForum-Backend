package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.notification.NotificationGetDto;
import com.thinkmore.forum.dto.notification.NotificationPostDto;
import com.thinkmore.forum.entity.Notification;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.exception.UserNotFoundException;
import com.thinkmore.forum.mapper.NotificationMapper;
import com.thinkmore.forum.repository.NotificationRepository;
import com.thinkmore.forum.repository.UsersRepository;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UsersRepository usersRepository;

    public List<NotificationGetDto> getNotificationsByUserId(UUID userId) {

        List<NotificationGetDto> all = notificationRepository.findByUsers_IdOrderByCreateTimestampDesc(userId).stream()
                .map(notificationMapper::fromEntity)
                .collect(Collectors.toList());

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
        notificationRepository.findByUsers_IdOrderByCreateTimestampDesc(userId)
                .forEach(notification -> {
                    notification.setViewed(true);
                    notificationRepository.save(notification);
                });

        return true;
    }
    @Transactional
    public Boolean createNewNotification(NotificationPostDto notificationPostDto, String type) {
        UUID users_id = UUID.fromString(Util.getJwtContext().get(0));
        Users user = usersRepository.findById(users_id)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));

        Map<String, String> contextMap = new HashMap<>();
        contextMap.put("follow_user", " followed you.");
        contextMap.put("reply", " replied your post.");
        contextMap.put("follow_post", " followed your post.");
        contextMap.put("reply_comment", " replied your comment.");
        Notification notification = notificationMapper.toEntity(notificationPostDto);
        notification.setContext(user.getUsername() + contextMap.get(type));
        notification.setImgUrl("http:not_data_so_just_test");
//        notification.setImgUrl(user.getProfileImg().getUrl());
        notificationRepository.save(notification);
        return true;
    }
}
