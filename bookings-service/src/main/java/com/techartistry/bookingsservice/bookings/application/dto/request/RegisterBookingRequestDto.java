package com.techartistry.bookingsservice.bookings.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegisterBookingRequestDto {
    @NotNull(message = "Room id is required")
    private Long roomId;

    @NotNull(message = "Check in date is required")
    private LocalDateTime checkInDate;

    @NotNull(message = "Check out date is required")
    private LocalDateTime checkOutDate;

    @NotBlank(message = "Student id is required")
    private String studentId;
}
