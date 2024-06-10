package com.techartistry.roomsservice.rooms.application.dto.response;

import com.techartistry.roomsservice.rooms.domain.entities.Lessor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class RoomResponseDto {
    private Long roomId;
    private String name;
    private String address;
    private List<String> amenities;
    private List<String> rules;
    private double price;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Lessor lessor;

    public RoomResponseDto() {}
}
