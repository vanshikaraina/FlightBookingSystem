package com.flightbooking.fbs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // allow all requests without authentication
                )
                .formLogin(form -> form.disable()) // disable Spring Security login
                .httpBasic(basic -> basic.disable()) // disable basic auth
                .logout(logout -> logout.disable()) // disable Spring Security logout
                .sessionManagement(session -> session
                        .maximumSessions(1) // allow 1 session per user
                        .maxSessionsPreventsLogin(false)
                );

        return http.build();
    }
}