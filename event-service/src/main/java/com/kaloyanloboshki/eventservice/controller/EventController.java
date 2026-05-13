package com.kaloyanloboshki.eventservice.controller;

import com.kaloyanloboshki.eventservice.model.dto.EventFilter;
import com.kaloyanloboshki.eventservice.model.entity.Event;
import com.kaloyanloboshki.eventservice.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getEvents(@Valid @RequestBody EventFilter eventFilter) {
        return ResponseEntity.ok(eventService.getEvents(eventFilter));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEvent(@PathVariable long eventId) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @PostMapping
    public ResponseEntity<Event> create(@Valid @RequestBody Event event) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(eventService.save(event));
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Event> update(@PathVariable long eventId, @Valid @RequestBody Event event) {
        return ResponseEntity.ok(eventService.update(eventId, event));
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> delete(@PathVariable long eventId) {
        eventService.delete(eventId);
        
        return ResponseEntity.noContent().build();
    }
}
