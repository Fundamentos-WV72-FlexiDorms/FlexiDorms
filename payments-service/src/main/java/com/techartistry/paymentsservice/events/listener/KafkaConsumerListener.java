package com.techartistry.paymentsservice.events.listener;

import com.techartistry.paymentsservice.events.model.BookingCreatedEvent;
import com.techartistry.paymentsservice.payments.application.services.StripeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Clase que escucha los eventos de kafka
 */
@Slf4j
@Component
public class KafkaConsumerListener {
    @Autowired
    private StripeService stripeService;

    @KafkaListener(
        topics = {"payments-events"},
        groupId = "my-group-id",
        containerFactory = "accountKafkaListenerFactory"
    )
    public void listener(BookingCreatedEvent eventData) {
        log.info("Received event with data = {}", eventData);

        try {
            stripeService.processPayment(eventData.getBookingId(), eventData.getTotalAmount());
            //bookingService.updatePaymentStatus(event.getBookingId(), EPaymentStatus.PAID);
        } catch (Exception e) {
            //bookingService.updatePaymentStatus(event.getBookingId(), EPaymentStatus.FAILED);
            log.error("Error processing payment", e);
        }
    }
}
