package com.flightbooking.fbs;

import com.flightbooking.fbs.entity.Role;
import com.flightbooking.fbs.entity.User;
import com.flightbooking.fbs.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if admin already exists
        if (userRepository.findByEmail("admin@gmail.com") == null) {
            User admin = User.builder()
                    .name("Admin")
                    .email("admin@gmail.com")
                    .password("admin123") // hash later if needed
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("Admin user created ✅");
        }
    }
}
