package com.kaloyanloboshki.bookingservice.model.dto;

import lombok.Data;

@Data
public class EventResponse {
    private Long id;
    private String title;
    private Integer availableSeats;
    private Integer totalSeats;
}
