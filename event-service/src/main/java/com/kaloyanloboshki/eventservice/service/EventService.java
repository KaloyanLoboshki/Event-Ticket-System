package com.kaloyanloboshki.eventservice.service;

import com.kaloyanloboshki.eventservice.EventSpecification;
import com.kaloyanloboshki.eventservice.exceptions.EventNotFoundException;
import com.kaloyanloboshki.eventservice.model.dto.EventFilter;
import com.kaloyanloboshki.eventservice.model.entity.Event;
import com.kaloyanloboshki.eventservice.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    public static final String EVENT_NOT_FOUND_MESSAGE = "Event with id: %d does not exists!";
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
        if (!eventRepository.existsById(eventId)) {
            throw new EventNotFoundException(String.format(EVENT_NOT_FOUND_MESSAGE, eventId));
        }
        event.setId(eventId);
        return eventRepository.save(event);
    }

    public void delete(long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(String.format(EVENT_NOT_FOUND_MESSAGE, eventId)));
        eventRepository.delete(event);
    }
}
