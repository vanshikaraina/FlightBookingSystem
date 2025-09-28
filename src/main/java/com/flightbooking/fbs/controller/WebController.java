package com.flightbooking.fbs.controller;

import com.flightbooking.fbs.entity.User;
import com.flightbooking.fbs.services.UserService;
import com.flightbooking.fbs.services.FlightService;
import com.flightbooking.fbs.services.BookingService;
import com.flightbooking.fbs.entity.Flight;
import com.flightbooking.fbs.entity.Role;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/web")
public class WebController {

    private final UserService userService;
    private final FlightService flightService;
    private final BookingService bookingService;

    public WebController(UserService userService, FlightService flightService, BookingService bookingService) {
        this.userService = userService;
        this.flightService = flightService;
        this.bookingService = bookingService;
    }

    // Landing/Home page
    @GetMapping({"/", "/home"})
    public String home() {
        return "home"; // or "index" if you want
    }

    // Registration page
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.registerUser(user);
        return "redirect:/web/login";
    }

    // Login page
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Optional<User> userOpt = userService.getUserByEmail(email);

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            User loggedInUser = userOpt.get();
            session.setAttribute("loggedInUser", loggedInUser);
            session.setAttribute("role", loggedInUser.getRole()); // store role separately
            return "redirect:/web/dashboard"; // go to dashboard after login
        }

        model.addAttribute("error", "Invalid email or password");
        return "login";
    }


    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/web/login";
    }

    // Dashboard
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) return "redirect:/web/login";

        model.addAttribute("user", loggedInUser);
        return "dashboard";
    }

    // Flights
    @GetMapping("/flights")
    public String showFlights(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) return "redirect:/web/login";

        model.addAttribute("flights", flightService.getAllFlights());
        return "flights";
    }

    // Book a flight
    @PostMapping("/book/{flightId}")
    public String bookFlight(@PathVariable Long flightId,
                             @RequestParam int seats,
                             HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) return "redirect:/web/login";

        bookingService.bookFlight(loggedInUser.getId(), null, flightId, null, seats);
        return "redirect:/web/bookings";
    }

    // View bookings
    @GetMapping("/bookings")
    public String viewBookings(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) return "redirect:/web/login";

        model.addAttribute("bookings", bookingService.getBookingsByUser(loggedInUser.getId()));
        return "bookings";
    }

    // Cancel booking
    @PostMapping("/cancel/{bookingId}")
    public String cancelBooking(@PathVariable Long bookingId,
                                @RequestParam int seatsToCancel,
                                HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) return "redirect:/web/login";

        bookingService.partialCancelBooking(bookingId, seatsToCancel);
        return "redirect:/web/bookings";
    }

    // Profile page
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) return "redirect:/web/login";

        model.addAttribute("user", loggedInUser);
        return "profile";
    }

    @GetMapping("/flights/manage")
    public String manageFlights(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            return "redirect:/web/login"; // only admin allowed
        }

        model.addAttribute("flights", flightService.getAllFlights());
        model.addAttribute("newFlight", new Flight()); // for adding flight
        return "manage-flights"; // new Thymeleaf page
    }

    // Add flight
    @PostMapping("/flights/add")
    public String addFlight(@ModelAttribute Flight flight, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || user.getRole() != Role.ADMIN) return "redirect:/web/login";

        flightService.addFlight(flight);
        return "redirect:/web/flights/manage";
    }

    // Delete flight
    @PostMapping("/flights/delete/{id}")
    public String deleteFlight(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || user.getRole() != Role.ADMIN) return "redirect:/web/login";

        flightService.deleteFlight(id);
        return "redirect:/web/flights/manage";
    }

    // Admin: Manage Users page
    @GetMapping("/users/manage")
    public String manageUsers(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            return "redirect:/web/login"; // only admin allowed
        }

        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User()); // form for adding new user
        return "manage-users"; // Thymeleaf page
    }

    // Admin: Add user
    @PostMapping("/users/add")
    public String addUser(@ModelAttribute User user, @RequestParam String role, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) return "redirect:/web/login";

        Role userRole = Role.valueOf(role); // convert string to Role enum
        userService.registerUserWithRole(user, userRole);

        return "redirect:/web/users/manage";
    }

    // Admin: Delete user
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) return "redirect:/web/login";

        userService.deleteUser(id);
        return "redirect:/web/users/manage";
    }

    // Admin: Manage Bookings page
    @GetMapping("/bookings/manage")
    public String manageBookings(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            return "redirect:/web/login"; // only admin allowed
        }

        model.addAttribute("bookings", bookingService.getAllBookings());
        return "manage-bookings"; // new Thymeleaf page
    }

    // Admin: Cancel booking
    @PostMapping("/bookings/cancel/{id}")
    public String cancelBooking(@PathVariable Long id, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) return "redirect:/web/login";

        bookingService.cancelBooking(id);
        return "redirect:/web/bookings/manage";
    }

    // Admin: Add booking for a user
    @PostMapping("/bookings/add")
    public String addBooking(@RequestParam Long userId,
                             @RequestParam Long flightId,
                             @RequestParam int seatsBooked,
                             HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || loggedInUser.getRole() != Role.ADMIN) {
            return "redirect:/web/login"; // only admin can add bookings
        }

        bookingService.bookFlight(userId, null, flightId, null, seatsBooked);
        return "redirect:/web/bookings/manage";
    }


}