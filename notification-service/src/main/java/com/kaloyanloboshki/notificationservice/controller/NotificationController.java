package com.kaloyanloboshki.notificationservice.controller;

import com.kaloyanloboshki.notificationservice.model.entity.Notification;
import com.kaloyanloboshki.notificationservice.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> notifications(@PathVariable long userId) {
        return ResponseEntity.ok(notificationService.notificationsByUserId(userId));
    }

    @GetMapping("/stream/{userId}")
    public SseEmitter streamNotifications(@PathVariable long userId) {
        return notificationService.subscribe(userId);
    }
}
