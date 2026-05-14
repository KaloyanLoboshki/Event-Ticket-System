package com.kaloyanloboshki.bookingservice.client;

import com.kaloyanloboshki.bookingservice.model.dto.EventResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PatchExchange;

@HttpExchange("/events")
public interface EventClient {

    @GetExchange("/{id}")
    EventResponse getEventById(@PathVariable long id);

    @PatchExchange("/{id}/seats/increment")
    void incrementSeats(@PathVariable long id, @RequestParam int quantity);

    @PatchExchange("/{id}/seats/decrement")
    void decrementSeats(@PathVariable long id, @RequestParam int quantity);
}
