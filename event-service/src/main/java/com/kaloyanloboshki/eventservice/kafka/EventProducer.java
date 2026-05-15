package com.kaloyanloboshki.eventservice.kafka;

import com.kaloyanloboshki.eventservice.model.entity.Event;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

    private final KafkaTemplate<String, Event> kafkaTemplate;

    public EventProducer(KafkaTemplate<String, Event> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEventCreated(Event event) {
        kafkaTemplate.send("event-created", event);
    }

    public void sendEventUpdated(Event event) {
        kafkaTemplate.send("event-updated", event);
    }
}
