package com.kaloyanloboshki.eventservice.service;

import com.kaloyanloboshki.eventservice.exceptions.EventNotFoundException;
import com.kaloyanloboshki.eventservice.exceptions.InsufficientSeatsException;
import com.kaloyanloboshki.eventservice.filter.EventSpecification;
import com.kaloyanloboshki.eventservice.kafka.EventProducer;
import com.kaloyanloboshki.eventservice.mapper.EventMapper;
import com.kaloyanloboshki.eventservice.model.dto.*;
import com.kaloyanloboshki.eventservice.model.entity.Event;
import com.kaloyanloboshki.eventservice.repository.EventRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventService {

    public static final String EVENT_NOT_FOUND_MESSAGE = "Event with id: %d does not exist!";
    public static final String NOT_ENOUGH_AVAILABLE_SEATS = "Not enough available seats!";

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final EventProducer eventProducer;

    public EventService(EventRepository eventRepository, EventMapper eventMapper, EventProducer eventProducer) {
        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.eventProducer = eventProducer;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "events", key = "#eventId")
    public EventResponse getEventById(long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(String.format(EVENT_NOT_FOUND_MESSAGE, eventId)));
        return eventMapper.toResponse(event);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "events", key = "#filter")
    public EventResponseList getEvents(EventFilter filter) {
        List<Event> events = eventRepository.findAll(EventSpecification.withFilter(filter));
        List<EventResponse> responses = events.stream()
                .map(eventMapper::toResponse)
                .toList();
        
        return new EventResponseList(responses);
    }

    @Transactional
    @CacheEvict(value = "events", allEntries = true)
    public EventResponse create(EventCreateRequest request) {
        Event event = eventMapper.toEntity(request);
        event.setAvailableSeats(request.getTotalSeats());

        Event savedEvent = eventRepository.save(event);
        eventProducer.sendEventCreated(savedEvent);
        return eventMapper.toResponse(savedEvent);
    }

    @Transactional
    @CacheEvict(value = "events", allEntries = true)
    public EventResponse update(long eventId, EventUpdateRequest request) {
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(String.format(EVENT_NOT_FOUND_MESSAGE, eventId)));

        eventMapper.updateEventFromDto(request, existingEvent);
        eventProducer.sendEventUpdated(existingEvent);

        return eventMapper.toResponse(existingEvent);
    }

    @Transactional
    @CacheEvict(value = "events", allEntries = true)
    public void delete(long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException(String.format(EVENT_NOT_FOUND_MESSAGE, eventId)));
        eventRepository.delete(event);
    }

    @Transactional
    @CacheEvict(value = "events", allEntries = true)
    public void increment(long eventId, int quantity) {
        Event event = eventRepository.findByIdWithLock(eventId)
                .orElseThrow(() -> new EventNotFoundException(String.format(EVENT_NOT_FOUND_MESSAGE, eventId)));

        event.setAvailableSeats(event.getAvailableSeats() + quantity);
    }

    @Transactional
    @CacheEvict(value = "events", allEntries = true)
    public void decrement(long eventId, int quantity) {
        Event event = eventRepository.findByIdWithLock(eventId)
                .orElseThrow(() -> new EventNotFoundException(String.format(EVENT_NOT_FOUND_MESSAGE, eventId)));

        if (event.getAvailableSeats() < quantity) {
            throw new InsufficientSeatsException(NOT_ENOUGH_AVAILABLE_SEATS);
        }
        event.setAvailableSeats(event.getAvailableSeats() - quantity);
    }
}
