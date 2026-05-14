package com.kaloyanloboshki.bookingservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBooking {
    private Long eventId;
    private Long userId;
    private Integer quantity;
}
