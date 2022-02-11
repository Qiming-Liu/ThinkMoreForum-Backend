package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.notification.NotificationGetDto;
import com.thinkmore.forum.service.NotificationService;
import com.thinkmore.forum.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    //Get all notifications in db ignoring userid, a function might not be recommended.
//    @GetMapping
//    public ResponseEntity<List<NotificationGetDto>> findAll() {
//        List<NotificationGetDto> notificationList = notificationService.getAllNotifications();
//        return ResponseEntity.ok(notificationList);
//    }

    @GetMapping(path = "/{notification_id}")
    public ResponseEntity<NotificationGetDto> findNotificationByIdAndUserId(@PathVariable("notification_id") String notification_id) {
        UUID notificationId = UUID.fromString(notification_id);
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(notificationService.getNotificationById(notificationId, userId));
    }

    @GetMapping
    public ResponseEntity<List<NotificationGetDto>> findNotificationsByUserId() {
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId));
    }

    @DeleteMapping(path = "/{notification_id}")
    public ResponseEntity<String> userDeleteNotification(@PathVariable("notification_id") String notification_id) {
        UUID notificationId = UUID.fromString(notification_id);
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(notificationService.userDeleteNotification(notificationId, userId));
    }
}
