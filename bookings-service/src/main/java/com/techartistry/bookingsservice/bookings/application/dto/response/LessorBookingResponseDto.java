package com.techartistry.bookingsservice.bookings.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techartistry.bookingsservice.bookings.domain.entities.Room;
import com.techartistry.bookingsservice.bookings.domain.entities.User;
import com.techartistry.bookingsservice.bookings.domain.enums.EBookingStatus;
import com.techartistry.bookingsservice.bookings.domain.enums.EPaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LessorBookingResponseDto {
    private Long bookingId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime bookingDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime checkInDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime checkOutDate;

    private double totalAmount;
    private EBookingStatus status;
    private EPaymentStatus paymentStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt;
    private Room room;
    private User student;
}
