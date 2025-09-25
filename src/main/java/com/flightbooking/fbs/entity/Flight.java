package com.flightbooking.fbs.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightNumber;
    private String airline;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int availableSeats;
    private double price;
}
