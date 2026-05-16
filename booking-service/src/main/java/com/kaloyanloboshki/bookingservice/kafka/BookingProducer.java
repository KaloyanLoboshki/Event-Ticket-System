package com.kaloyanloboshki.bookingservice.kafka;

import com.kaloyanloboshki.bookingservice.model.entity.Booking;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookingProducer {

    private final KafkaTemplate<String, Booking> kafkaTemplate;

    public BookingProducer(KafkaTemplate<String, Booking> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTicketBooked(Booking booking) {
        kafkaTemplate.send("ticket-booked", booking);
    }

    public void sendTicketCancelled(Booking booking) {
        kafkaTemplate.send("ticket-cancelled", booking);
    }
}
