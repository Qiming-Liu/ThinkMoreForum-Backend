package com.thinkmore.forum.controller;

import com.thinkmore.forum.dto.notification.NotificationGetDto;
import com.thinkmore.forum.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
