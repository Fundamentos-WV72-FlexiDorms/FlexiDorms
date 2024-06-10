package com.techartistry.bookingsservice.bookings.domain.events;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookingCreatedEvent {
    private Long bookingId;
    private double totalAmount;

    public BookingCreatedEvent() {}

    public BookingCreatedEvent(Long bookingId, double totalAmount) {
        this.bookingId = bookingId;
        this.totalAmount = totalAmount;
    }
}