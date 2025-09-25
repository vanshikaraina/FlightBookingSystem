package com.flightbooking.fbs.controller;

import com.flightbooking.fbs.entity.Booking;
import com.flightbooking.fbs.services.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // Book a flight
    @PostMapping("/book")
    public ResponseEntity<Booking> bookFlight(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) Long flightId,
            @RequestParam(required = false) String flightNumber,
            @RequestParam int seatsBooked) {

        try {
            Booking booking = bookingService.bookFlight(userId, userName, flightId, flightNumber, seatsBooked);
            return ResponseEntity.ok(booking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);  // Invalid input
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();      // Flight or User not found
        }
    }


    // Cancel booking
    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        boolean deleted = bookingService.cancelBooking(id);
        if (!deleted) {
            return ResponseEntity.notFound().build(); // 404 if booking not found
        }
        return ResponseEntity.noContent().build();    // 204 if deleted successfully
    }

    // Get all bookings
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // Get booking by ID
    @GetMapping("/{id}")
    public Optional<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    // Get bookings for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.getBookingsByUser(userId);
        if (bookings.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404 if no bookings
        }
        return ResponseEntity.ok(bookings);
    }


}
