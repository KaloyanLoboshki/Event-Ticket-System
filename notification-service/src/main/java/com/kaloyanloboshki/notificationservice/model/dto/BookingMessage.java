package com.kaloyanloboshki.notificationservice.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingMessage {

    private Long id;
    private Long eventId;
    private Long userId;
    private Integer quantity;
    private BookingStatus status;
    private LocalDateTime createdAt;
}
