package com.techartistry.bookingsservice.events.service;

import com.techartistry.bookingsservice.bookings.domain.events.BookingCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, BookingCreatedEvent> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String,BookingCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishBookingCreatedEvent(BookingCreatedEvent eventData) {
        kafkaTemplate.send("account-events", eventData);
    }
}
