package com.techartistry.bookingsservice.bookings.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
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
    @FutureOrPresent(message = "Check in date must be in the present or future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime checkInDate;

    @NotNull(message = "Check out date is required")
    @Future(message = "Check out date must be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime checkOutDate;

    @NotBlank(message = "Student id is required")
    private String studentId;
}
