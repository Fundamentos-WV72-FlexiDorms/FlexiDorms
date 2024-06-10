package com.techartistry.bookingsservice.bookings.application.dto.request;

import com.techartistry.bookingsservice.bookings.domain.enums.EBookingStatus;
import com.techartistry.bookingsservice.bookings.domain.enums.EPaymentStatus;

import java.time.LocalDateTime;

public record UpdateBookingRequestDto (
    LocalDateTime checkInDate,
    LocalDateTime checkOutDate,
    EBookingStatus status,
    EPaymentStatus paymentStatus
) {}
