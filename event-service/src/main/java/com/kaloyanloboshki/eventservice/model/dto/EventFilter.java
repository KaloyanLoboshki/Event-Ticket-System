package com.kaloyanloboshki.eventservice.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EventFilter {
    private String category;
    private String location;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private BigDecimal price;
}
