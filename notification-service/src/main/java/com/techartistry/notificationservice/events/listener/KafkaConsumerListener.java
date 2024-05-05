package com.techartistry.notificationservice.events.listener;

import com.techartistry.notificationservice.email.service.IEmailService;
import com.techartistry.notificationservice.events.model.UserSignedUpEventData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Clase que escucha los eventos de kafka
 */
@Slf4j
@Component
public class KafkaConsumerListener {
    private final IEmailService emailService;

    public KafkaConsumerListener(IEmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(
        topics = "common_events",
        groupId = "group_id",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(String message) {
        log.info("Consumed message: {}", message);
    }

    @KafkaListener(
        topics = {"account-events"},
        groupId = "my-group-id",
        containerFactory = "accountKafkaListenerFactory"
    )
    public void listener(UserSignedUpEventData eventData) {
        log.info("Received event with data = {}", eventData);
        emailService.sendVerificationEmail(
                eventData.getFullName(),
                eventData.getEmail(),
                eventData.getToken()
        );
    }
}
