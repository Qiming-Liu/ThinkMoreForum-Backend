package com.thinkmore.forum.service;

import com.thinkmore.forum.dto.notification.NotificationGetDto;
import com.thinkmore.forum.dto.notification.NotificationPostDto;
import com.thinkmore.forum.entity.Notification;
import com.thinkmore.forum.entity.Users;
import com.thinkmore.forum.exception.UserNotFoundException;
import com.thinkmore.forum.mapper.NotificationMapper;
import com.thinkmore.forum.repository.NotificationRepository;
import com.thinkmore.forum.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UsersRepository usersRepository;

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
    public Boolean postNotification(NotificationPostDto notificationPostDto, String type, UUID usersId) {

        Users user = usersRepository.findById(usersId)
                .orElseThrow(() -> new UserNotFoundException("Invalid UserID"));

        String context;
        switch (type) {
            case "follow_user": {
                context = " followed you.";
                break;
            }
            case "reply": {
                context = " replied your post.";
                break;
            }
            case "follow_post": {
                context = " followed your post.";
                break;
            }
            case "reply_comment": {
                context = " replied your comment.";
                break;
            }
            default: {
                context = type;
                break;
            }
        }
        Notification notification = notificationMapper.toEntity(notificationPostDto);
        notification.setContext(user.getUsername() + context);
        notification.setImgUrl(user.getProfileImgUrl());
        notificationRepository.save(notification);
        return true;
    }
}
