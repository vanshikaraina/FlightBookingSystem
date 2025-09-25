package com.flightbooking.fbs.repository;

import com.flightbooking.fbs.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);   // For getting bookings by user
    List<Booking> findByFlightId(Long flightId);
}
