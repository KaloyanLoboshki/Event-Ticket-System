package com.kaloyanloboshki.notificationservice.service;

import com.kaloyanloboshki.notificationservice.model.entity.Notification;
import com.kaloyanloboshki.notificationservice.repository.NotificationRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public SseEmitter subscribe(long userId) {
        SseEmitter emitter = new SseEmitter(1800000L);
        emitters.put(userId, emitter);

        emitter.onCompletion(() -> emitters.remove(userId));
        emitter.onTimeout(() -> emitters.remove(userId));
        emitter.onError((e) -> emitters.remove(userId));

        return emitter;
    }

    @Async("notificationExecutor")
    public void sendNotification(long userId, Notification notification) {
        SseEmitter emitter = emitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(notification);
            } catch (IOException e) {
                emitters.remove(userId);
            }
        }
    }

    public List<Notification> notificationsByUserId(long userId) {
        return notificationRepository.findAllByUserId(userId);
    }
}
