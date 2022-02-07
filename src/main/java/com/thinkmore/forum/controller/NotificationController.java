package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.notification.NotificationGetDto;
import com.thinkmore.forum.service.NotificationService;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(path = "/get_notification")
    public ResponseEntity<List<NotificationGetDto>> findAll() {
        List<NotificationGetDto> notificationList = notificationService.getAllNotifications();
        return ResponseEntity.ok(notificationList);
    }

    @GetMapping(path = "/get_notification/{notification_id}")
    public ResponseEntity<NotificationGetDto> findNotificationById(@PathVariable("notification_id") UUID notificationId) {
        return ResponseEntity.ok(notificationService.getNotificationById(notificationId));
    }

    @GetMapping(path = "/get_notification/my_notification")
    public ResponseEntity<List<NotificationGetDto>> findNotificationByUserId() {
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId));
    }

    @DeleteMapping
    public ResponseEntity<String> userDeleteNotification(@RequestParam String notification_id) {
        UUID notificationId = UUID.fromString(notification_id);
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(notificationService.userDeleteNotification(notificationId, userId));
    }
}
