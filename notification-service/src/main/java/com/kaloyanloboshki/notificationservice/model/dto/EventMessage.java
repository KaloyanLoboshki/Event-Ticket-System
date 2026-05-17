package com.kaloyanloboshki.notificationservice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EventMessage {

    private long id;
    private String title;
    private String location;
    private String category;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;

    private BigDecimal price;
    private Integer availableSeats;
    private Integer totalSeats;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
