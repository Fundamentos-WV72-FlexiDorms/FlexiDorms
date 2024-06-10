package com.techartistry.bookingsservice.bookings.domain.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad Rooms (de rooms-service)
 */
@Setter
@Getter
public class Room {
    private Long roomId;
    private String name;
    private String address;
    private List<String> amenities;
    private List<String> rules;
    private double price;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User lessor;

    public Room() {}
}
