package com.kaloyanloboshki.notificationservice.model.entity;

import com.kaloyanloboshki.notificationservice.model.dto.NotificationType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private NotificationType type;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
