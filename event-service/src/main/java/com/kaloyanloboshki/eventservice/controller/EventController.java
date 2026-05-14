package com.kaloyanloboshki.eventservice.controller;

import com.kaloyanloboshki.eventservice.model.dto.EventFilter;
import com.kaloyanloboshki.eventservice.model.entity.Event;
import com.kaloyanloboshki.eventservice.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getEvents(@ModelAttribute EventFilter eventFilter) {
        return ResponseEntity.ok(eventService.getEvents(eventFilter));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable long eventId) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event event) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventService.save(event));
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable long eventId, @Valid @RequestBody Event event) {
        return ResponseEntity.ok(eventService.update(eventId, event));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable long eventId) {
        eventService.delete(eventId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/seats/increment")
    public ResponseEntity<Void> incrementSeats(@PathVariable long id, @RequestParam int quantity) {
        eventService.increment(id, quantity);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/seats/decrement")
    public ResponseEntity<Void> decrementSeats(@PathVariable long id, @RequestParam int quantity) {
        eventService.decrement(id, quantity);
        return ResponseEntity.ok().build();
    }
}
