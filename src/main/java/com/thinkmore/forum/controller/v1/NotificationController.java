package com.thinkmore.forum.controller.v1;

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

    @GetMapping
    public ResponseEntity<List<NotificationGetDto>> getNotifications() {
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId));
    }

    @GetMapping(path = "/viewed/{notificationId}")
    public ResponseEntity<Boolean> markAsViewed(@PathVariable String notificationId) {
        UUID notificationIdUuid = UUID.fromString(notificationId);
        return ResponseEntity.ok(notificationService.markAsViewed(notificationIdUuid));
    }

    @GetMapping(path = "/viewed_all")
    public ResponseEntity<Boolean> viewNotification() {
        UUID userId = UUID.fromString(Util.getJwtContext().get(0));
        return ResponseEntity.ok(notificationService.markAllAsViewed(userId));
    }
}