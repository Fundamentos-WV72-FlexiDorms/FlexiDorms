package com.techartistry.bookingsservice.bookings.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techartistry.bookingsservice.bookings.domain.enums.EBookingStatus;
import com.techartistry.bookingsservice.bookings.domain.enums.EPaymentStatus;

import java.time.LocalDateTime;

public record UpdateBookingRequestDto (
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime checkInDate,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime checkOutDate,
    EBookingStatus status,
    EPaymentStatus paymentStatus
) {}
