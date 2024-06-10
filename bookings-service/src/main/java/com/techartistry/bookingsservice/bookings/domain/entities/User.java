package com.techartistry.bookingsservice.bookings.domain.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidad Usuario (de accounts-service)
 */
@Setter
@Getter
public class User {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String dni;
    private String gender;
    private boolean emailVerified;
    private boolean enabled;
    private LocalDateTime createdTimestamp;

    public User() {}
}
