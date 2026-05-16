package com.kaloyanloboshki.notificationservice.client;

import com.kaloyanloboshki.notificationservice.model.dto.Booking;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange("/bookings")
public interface BookingClient {

    @GetMapping("event/{eventId}")
    List<Booking> bookingsPerEvent(@PathVariable long eventId);
}
