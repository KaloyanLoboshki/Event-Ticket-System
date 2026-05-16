package com.kaloyanloboshki.bookingservice.repository;

import com.kaloyanloboshki.bookingservice.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b where b.userId = :userId")
    List<Booking> findByUserId(long userId);

    @Query("select b from Booking b where b.eventId = :eventId")
    List<Booking> findAllByEventId(long eventId);
}
