INSERT INTO `flight` (flight_number, airline, source, destination, departure_time, arrival_time, available_seats, price)
VALUES ('AI101', 'Air India', 'Delhi', 'Mumbai', '2025-09-10 08:00', '2025-09-10 10:00', 120, 5500.00);

INSERT INTO `flight` (flight_number, airline, source, destination, departure_time, arrival_time, available_seats, price)
VALUES ('6E202', 'IndiGo', 'Bangalore', 'Chennai', '2025-09-11 09:00', '2025-09-11 10:30', 100, 3000.00);

INSERT INTO `flight` (flight_number, airline, source, destination, departure_time, arrival_time, available_seats, price)
VALUES ('SG303', 'SpiceJet', 'Kolkata', 'Delhi', '2025-09-12 07:30', '2025-09-12 10:15', 150, 4800.00);


-- Users
INSERT INTO `users` (name, email, password) VALUES ('Tia Chugh', 'tia@example.com', 'password123');
INSERT INTO `users` (name, email, password) VALUES ('Vanshika', 'vanshika@example.com', 'pass456');
INSERT INTO `users` (name, email, password) VALUES ('siya kakkar', 'siya@example.com', 'pass789');

-- Bookings

--INSERT INTO booking (user_id, flight_id, seats_booked, booking_date, status)
--VALUES (1, 1, 2, '2025-09-06', 'CONFIRMED');
--
--
--INSERT INTO booking (user_id, flight_id, seats_booked, booking_date, status)
--VALUES (2, 2, 1, '2025-09-06', 'CONFIRMED');
--
--
--INSERT INTO booking (user_id, flight_id, seats_booked, booking_date, status)
--VALUES (3, 3, 3, '2025-09-06', 'CONFIRMED');


-- Bookings (subqueries ensure correct IDs)
INSERT INTO `booking` (user_id, flight_id, seats_booked, booking_date, status)
VALUES (
    (SELECT id FROM users WHERE email='tia@example.com'),
    (SELECT id FROM flight WHERE flight_number='AI101'),
    2,
    '2025-09-06',
    'CONFIRMED'
);

INSERT INTO `booking` (user_id, flight_id, seats_booked, booking_date, status)
VALUES (
    (SELECT id FROM users WHERE email='vanshika@example.com'),
    (SELECT id FROM flight WHERE flight_number='6E202'),
    1,
    '2025-09-06',
    'CONFIRMED'
);

INSERT INTO `booking` (user_id, flight_id, seats_booked, booking_date, status)
VALUES (
    (SELECT id FROM users WHERE email='siya@example.com'),
    (SELECT id FROM flight WHERE flight_number='SG303'),
    3,
    '2025-09-06',
    'CONFIRMED'
);