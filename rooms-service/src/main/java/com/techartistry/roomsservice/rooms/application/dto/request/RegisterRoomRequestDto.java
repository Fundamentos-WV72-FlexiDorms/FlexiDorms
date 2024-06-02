package com.techartistry.roomsservice.rooms.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Setter
@Getter
public class RegisterRoomRequestDto {
    @NotBlank(message = "Room name is required")
    private String name;

    @NotBlank(message = "Room address is required")
    private String address;

    @NotNull(message = "Room amenities are required")
    private List<String> amenities;

    @NotNull(message = "Room rules are required")
    private List<String> rules;

    @Min(value = 0, message = "Room price must be greater than 0")
    private double price;

    @URL(message = "Invalid image URL")
    private String imageUrl;

    @NotNull(message = "Lessor ID is required")
    private String lessorId;

    public RegisterRoomRequestDto() {}
}