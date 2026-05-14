package com.kaloyanloboshki.eventservice.service;

import com.kaloyanloboshki.eventservice.EventSpecification;
import com.kaloyanloboshki.eventservice.exceptions.EventNotFoundException;
import com.kaloyanloboshki.eventservice.exceptions.InsufficientSeatsException;
import com.kaloyanloboshki.eventservice.model.dto.EventFilter;
import com.kaloyanloboshki.eventservice.model.entity.Event;
import com.kaloyanloboshki.eventservice.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService {

    public static final String EVENT_NOT_FOUND_MESSAGE = "Event with id: %d does not exists!";
    public static final String NOT_ENOUGH_AVAILABLE_SEATS = "Not enough available seats!";
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event getEventById(long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(String.format(EVENT_NOT_FOUND_MESSAGE, eventId)));
    }

    public List<Event> getEvents(EventFilter filter) {
        return eventRepository.findAll(EventSpecification.withFilter(filter));
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public Event update(long eventId, Event event) {
        eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(String.format(EVENT_NOT_FOUND_MESSAGE, eventId)));
        event.setId(eventId);

        return eventRepository.save(event);
    }

    public void delete(long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(String.format(EVENT_NOT_FOUND_MESSAGE, eventId)));

        eventRepository.delete(event);
    }

    @Transactional
    public void increment(long eventId, int quantity) {
        Event event = eventRepository.findByIdWithLock(eventId)
                .orElseThrow(() -> new EventNotFoundException(String.format(EVENT_NOT_FOUND_MESSAGE, eventId)));

        event.setAvailableSeats(event.getAvailableSeats() + quantity);
    }

    @Transactional
    public void decrement(long eventId, int quantity) {
        Event event = eventRepository.findByIdWithLock(eventId)
                .orElseThrow(() -> new EventNotFoundException(String.format(EVENT_NOT_FOUND_MESSAGE, eventId)));

        if (event.getAvailableSeats() < quantity) {
            throw new InsufficientSeatsException(NOT_ENOUGH_AVAILABLE_SEATS);
        }
        event.setAvailableSeats(event.getAvailableSeats() - quantity);
    }
}
