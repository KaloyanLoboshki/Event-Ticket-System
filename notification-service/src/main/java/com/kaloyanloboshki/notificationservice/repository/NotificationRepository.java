package com.kaloyanloboshki.notificationservice.repository;

import com.kaloyanloboshki.notificationservice.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
