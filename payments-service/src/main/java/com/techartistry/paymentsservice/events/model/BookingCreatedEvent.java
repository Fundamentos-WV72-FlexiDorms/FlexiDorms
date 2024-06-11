package com.techartistry.paymentsservice.events.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa los datos del evento
 */
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