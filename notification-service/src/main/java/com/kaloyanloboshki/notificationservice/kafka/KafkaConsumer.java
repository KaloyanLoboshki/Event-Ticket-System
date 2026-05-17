package com.kaloyanloboshki.notificationservice.kafka;

import com.kaloyanloboshki.notificationservice.client.BookingClient;
import com.kaloyanloboshki.notificationservice.client.PreferenceClient;
import com.kaloyanloboshki.notificationservice.model.dto.Booking;
import com.kaloyanloboshki.notificationservice.model.dto.BookingMessage;
import com.kaloyanloboshki.notificationservice.model.dto.EventMessage;
import com.kaloyanloboshki.notificationservice.model.dto.NotificationType;
import com.kaloyanloboshki.notificationservice.model.entity.Notification;
import com.kaloyanloboshki.notificationservice.repository.NotificationRepository;
import com.kaloyanloboshki.notificationservice.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaConsumer {

    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    private final BookingClient bookingClient;
    private final PreferenceClient preferenceClient;

    public KafkaConsumer(NotificationRepository notificationRepository, NotificationService notificationService, BookingClient bookingClient, PreferenceClient preferenceClient) {
        this.notificationRepository = notificationRepository;
        this.notificationService = notificationService;
        this.bookingClient = bookingClient;
        this.preferenceClient = preferenceClient;
    }


    @KafkaListener(topics = "event-created", groupId = "notification-group", properties = {
            "spring.json.value.default.type=com.kaloyanloboshki.notificationservice.model.dto.EventMessage"
    })
    public void sendEventCreatedNotification(EventMessage eventMessage) {
        List<Long> userIds = preferenceClient.getUserIdsByPreferenceCategory(eventMessage.getCategory());

        List<Notification> notifications = userIds.stream().map(userId -> {
            Notification notification = new Notification();
            notification.setUserId(userId);
            notification.setMessage("New event in " + eventMessage.getCategory() + ": " + eventMessage.getTitle());
            notification.setType(NotificationType.EVENT_CREATED);

            return notification;
        }).toList();

        notificationRepository.saveAll(notifications);
        notifications.forEach(notification -> notificationService.sendNotification(notification.getUserId(), notification));
    }

    @KafkaListener(topics = "event-updated", groupId = "notification-group", properties = {
            "spring.json.value.default.type=com.kaloyanloboshki.notificationservice.model.dto.EventMessage"
    })
    public void sendEventUpdatedNotification(EventMessage eventMessage) {
        List<Booking> bookings = bookingClient.bookingsPerEvent(eventMessage.getId());

        List<Notification> notifications = bookings.stream()
                .map(booking -> {
                    Notification notification = new Notification();
                    notification.setUserId(booking.getUserId());
                    notification.setMessage("Your event: " + eventMessage.getId() + " has been updated");
                    notification.setType(NotificationType.EVENT_UPDATED);

                    return notification;
                })
                .toList();

        notificationRepository.saveAll(notifications);
        notifications.forEach(notification -> notificationService.sendNotification(notification.getUserId(), notification));
    }

    @KafkaListener(topics = "ticket-booked", groupId = "notification-group", properties = {
            "spring.json.value.default.type=com.kaloyanloboshki.notificationservice.model.dto.BookingMessage"
    })
    public void sendTicketBookedNotification(BookingMessage bookingMessage) {
        Notification notification = new Notification();
        notification.setUserId(bookingMessage.getUserId());
        notification.setMessage("Your booking for event " + bookingMessage.getEventId() + " is confirmed!");
        notification.setType(NotificationType.TICKET_BOOKED);

        notificationRepository.save(notification);
        notificationService.sendNotification(notification.getUserId(), notification);
    }

    @KafkaListener(topics = "ticket-cancelled", groupId = "notification-group", properties = {
            "spring.json.value.default.type=com.kaloyanloboshki.notificationservice.model.dto.BookingMessage"
    })
    public void sendTicketCancelledNotification(BookingMessage bookingMessage) {
        Notification notification = new Notification();
        notification.setUserId(bookingMessage.getUserId());
        notification.setMessage("Your booking for event " + bookingMessage.getEventId() + " is canceled!");
        notification.setType(NotificationType.TICKET_CANCELLED);

        notificationRepository.save(notification);
        notificationService.sendNotification(notification.getUserId(), notification);
    }
}
