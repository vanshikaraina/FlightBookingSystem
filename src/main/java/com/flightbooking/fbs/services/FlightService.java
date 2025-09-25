package com.flightbooking.fbs.services;

import com.flightbooking.fbs.entity.Flight;
import com.flightbooking.fbs.repository.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    // Add Flight
    public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    // Update Flight
    public Flight updateFlight(Long id, Flight updatedFlight) {
        Optional<Flight> optionalFlight = flightRepository.findById(id);
        if (optionalFlight.isPresent()) {
            Flight flight = optionalFlight.get();
            flight.setFlightNumber(updatedFlight.getFlightNumber());
            flight.setAirline(updatedFlight.getAirline());
            flight.setSource(updatedFlight.getSource());
            flight.setDestination(updatedFlight.getDestination());
            flight.setDepartureTime(updatedFlight.getDepartureTime());
            flight.setArrivalTime(updatedFlight.getArrivalTime());
            flight.setAvailableSeats(updatedFlight.getAvailableSeats());
            flight.setPrice(updatedFlight.getPrice());
            return flightRepository.save(flight);
        }
        return null; // or throw exception later
    }

    // Delete Flight
    public boolean deleteFlight(Long id) {
        if (flightRepository.existsById(id)) {
            flightRepository.deleteById(id);
            return true;
        }
        return false;
    }


    // Get All Flights
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // Search Flights by source & destination
    public List<Flight> searchFlights(String source, String destination) {
        return flightRepository.findBySourceAndDestination(source, destination);
    }
    // Get Flight by ID
    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }

}
