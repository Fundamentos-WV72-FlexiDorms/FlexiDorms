package com.techartistry.roomsservice.rooms.domain.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidad Lessor (usuario de accounts-service)
 */
@Setter
@Getter
public class Lessor {
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

    public Lessor() {}
}
