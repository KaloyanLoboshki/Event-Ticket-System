package com.kaloyanloboshki.eventservice.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
public class EventFilter {
    private String category;
    private String location;
    private ZonedDateTime startsAt;
    private ZonedDateTime endsAt;
    private BigDecimal price;
}
