package com.techartistry.bookingsservice.bookings.application.dto.response;

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
    private LocalDateTime bookingDate;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private double totalAmount;
    private EBookingStatus status;
    private EPaymentStatus paymentStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Room room;
    private User student;
}
