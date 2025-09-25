package com.flightbooking.fbs.controller;

import com.flightbooking.fbs.entity.User;
import com.flightbooking.fbs.services.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.flightbooking.fbs.entity.Role; // <-- add this at the top

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    // Register new user
//    @PostMapping("/register")
//    public User registerUser(@RequestBody User user) {
//        return userService.registerUser(user);
//    }

    // Get user by ID
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    // UserController.java
    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }


    // Get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> userOpt = userService.getUserByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 if not found
        }
        return ResponseEntity.ok(userOpt.get());
    }


    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id); // call service method
        if (deleted) {
            return ResponseEntity.ok("User deleted successfully ✅");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User with ID " + id + " not found ❌");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // Assign default role if none provided
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        User savedUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Optional<User> userOpt = userService.getUserByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // User not found
        }

        User user = userOpt.get();
        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Invalid password
        }

        return ResponseEntity.ok(user); // Login successful
    }



}
