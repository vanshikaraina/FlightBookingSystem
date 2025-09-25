package com.flightbooking.fbs.services;

import com.flightbooking.fbs.entity.User;
import com.flightbooking.fbs.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List; // import this
import java.util.Optional;
import com.flightbooking.fbs.entity.Role;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register new user
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    // Get user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Get user by email (useful for login later)
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }
    public boolean deleteUser(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            userRepository.deleteById(id);
            return true; // deleted successfully
        }
        return false; // user not found
    }
    // NEW METHOD: Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User registerUserWithRole(User user, Role role) {
        user.setRole(role); // assign role
        return userRepository.save(user);
    }


}
