package com.flightbooking.fbs.controller;

import com.flightbooking.fbs.entity.Flight;
import com.flightbooking.fbs.services.FlightService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    // Add flight
    @PostMapping
    public Flight addFlight(@RequestBody Flight flight) {
        return flightService.addFlight(flight);
    }

    //update flight
    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flightDetails) {
        Flight updated = flightService.updateFlight(id, flightDetails);
        if (updated == null) {
            return ResponseEntity.notFound().build(); // 404 if flight not found
        }
        return ResponseEntity.ok(updated);
    }
    //delete flight
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        boolean deleted = flightService.deleteFlight(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }


    // Get all flights
    @GetMapping
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    // Get flight by ID
    @GetMapping("/{id}")
    public Optional<Flight> getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }

    // Search flights (by source, destination, date)
    @GetMapping("/search")
    public List<Flight> searchFlights(@RequestParam String source,
                                      @RequestParam String destination,
                                      @RequestParam String date) {
        return flightService.searchFlights(source, destination); //removed date
    }
}
