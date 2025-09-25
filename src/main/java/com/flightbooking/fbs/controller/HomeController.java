package com.flightbooking.fbs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {

        List<Map<String, String>> endpoints = List.of(
                // Flights
                Map.of("url", "/api/flights", "method", "GET", "description", "View all flights"),
                Map.of("url", "/api/flights/{id}", "method", "GET", "description", "View flight by ID"),
                Map.of("url", "/api/flights/search?source={source}&destination={destination}", "method", "GET", "description", "Search flights"),
                Map.of("url", "/api/flights", "method", "POST", "description", "Add a new flight"),
                Map.of("url", "/api/flights/{id}", "method", "PUT", "description", "Update flight by ID"),
                Map.of("url", "/api/flights/{id}", "method", "DELETE", "description", "Delete a flight"),
                // H2 Console
                Map.of("url", "/h2-console", "method", "GET", "description", "Open H2 Database Console"),
                // Bookings
                Map.of("url", "/api/bookings", "method", "GET", "description", "View all bookings"),
                Map.of("url", "/api/bookings/{id}", "method", "GET", "description", "View booking by ID"),
                Map.of("url", "/api/bookings/book?userId={userId}&flightId={flightId}", "method", "POST", "description", "Book a flight"),
                Map.of("url", "/api/bookings/cancel/{id}", "method", "DELETE", "description", "Cancel a booking"),
                // Users
                Map.of("url", "/api/users/register", "method", "POST", "description", "Register a new user"),
                Map.of("url", "/api/users/{id}", "method", "GET", "description", "Get user by ID"),
                Map.of("url", "/api/users/email/{email}", "method", "GET", "description", "Get user by email"),
                Map.of("url", "/api/users/{id}", "method", "DELETE", "description", "Delete user by ID")
        );

        return Map.of(
                "message", "Welcome to the Flight Booking System API ✈️",
                "endpoints", endpoints
        );
    }
}
