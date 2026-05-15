package com.kaloyanloboshki.bookingservice.service;

import com.kaloyanloboshki.bookingservice.client.EventClient;
import com.kaloyanloboshki.bookingservice.exceptions.BookingNotFoundException;
import com.kaloyanloboshki.bookingservice.kafka.BookingProducer;
import com.kaloyanloboshki.bookingservice.model.BookingStatus;
import com.kaloyanloboshki.bookingservice.model.dto.CreateBooking;
import com.kaloyanloboshki.bookingservice.model.entity.Booking;
import com.kaloyanloboshki.bookingservice.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    public static final String BOOKING_NOT_FOUND_MESSAGE = "Booking with id: %d does not exists!";
    private final BookingRepository bookingRepository;
    private final EventClient eventClient;
    private final BookingProducer bookingProducer;

    public BookingService(BookingRepository bookingRepository, EventClient eventClient, BookingProducer bookingProducer) {
        this.bookingRepository = bookingRepository;
        this.eventClient = eventClient;
        this.bookingProducer = bookingProducer;
    }

    public Booking save(CreateBooking booking) {
        eventClient.decrementSeats(booking.getEventId(), booking.getQuantity());

        Booking newBooking = Booking.builder()
                .eventId(booking.getEventId())
                .userId(booking.getUserId())
                .quantity(booking.getQuantity())
                .status(BookingStatus.CONFIRMED)
                .build();

        bookingProducer.sendTicketBooked(newBooking);

        Booking savedBooking = bookingRepository.save(newBooking);
        bookingProducer.sendTicketBooked(savedBooking);

        return savedBooking;
    }

    public void cancelBooking(long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(String.format(BOOKING_NOT_FOUND_MESSAGE, bookingId)));

        eventClient.incrementSeats(booking.getEventId(), booking.getQuantity());
        bookingRepository.delete(booking);
        bookingProducer.sendTicketCancelled(booking);
    }

    public List<Booking> bookingsPerUser(long userId) {
        return bookingRepository.findByUserId(userId);
    }
}
