package com.flightbooking.fbs.services;

import com.flightbooking.fbs.entity.Booking;
import com.flightbooking.fbs.entity.Flight;
import com.flightbooking.fbs.entity.User;
import com.flightbooking.fbs.repository.BookingRepository;
import com.flightbooking.fbs.repository.FlightRepository;
import com.flightbooking.fbs.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository,
                          FlightRepository flightRepository,
                          UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
    }

    // Book a flight
    public Booking bookFlight(Long userId, String userName, Long flightId, String flightNumber, int seatsBooked) {

        Optional<User> userOpt = (userId != null) ?
                userRepository.findById(userId) :
                userRepository.findByName(userName);

        Optional<Flight> flightOpt = (flightId != null) ?
                flightRepository.findById(flightId) :
                flightRepository.findByFlightNumber(flightNumber);

        if (userOpt.isEmpty() || flightOpt.isEmpty()) {
            throw new RuntimeException("User or Flight not found");
        }

        Booking booking = new Booking();
        booking.setUser(userOpt.get());
        booking.setFlight(flightOpt.get());
        booking.setSeatsBooked(seatsBooked);
        booking.setBookingDate(java.time.LocalDate.now().toString());
        booking.setStatus("CONFIRMED");

        return bookingRepository.save(booking);
    }


    // Cancel a booking
    public boolean cancelBooking(Long bookingId) {
        if (bookingRepository.existsById(bookingId)) {
            bookingRepository.deleteById(bookingId);
            return true;   // booking existed and was deleted
        }
        return false;      // booking didn't exist
    }


    // Get all bookings for a user
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    // Get all bookings for a flight
    public List<Booking> getBookingsByFlight(Long flightId) {
        return bookingRepository.findByFlightId(flightId);
    }

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Get booking by ID
    public Optional<Booking> getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId);
    }

}
