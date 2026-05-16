package com.kaloyanloboshki.bookingservice.controller;

import com.kaloyanloboshki.bookingservice.model.dto.CreateBooking;
import com.kaloyanloboshki.bookingservice.model.entity.Booking;
import com.kaloyanloboshki.bookingservice.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody CreateBooking booking) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.save(booking));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<Booking>> bookingsPerUser(@PathVariable long userId) {
        return ResponseEntity.ok(bookingService.bookingsPerUser(userId));
    }

    @GetMapping("event/{eventId}")
    public ResponseEntity<List<Booking>> bookingsPerEvent(@PathVariable long eventId) {
        return ResponseEntity.ok(bookingService.bookingsPerEvent(eventId));
    }
}
