//package com.flightbooking.fbs.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Table(name = "users") // avoid reserved keyword issues
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//    private String email;
//    private String password;
//}

package com.flightbooking.fbs.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)   // store role as string (e.g., "ADMIN", "USER")
    private Role role;
}
