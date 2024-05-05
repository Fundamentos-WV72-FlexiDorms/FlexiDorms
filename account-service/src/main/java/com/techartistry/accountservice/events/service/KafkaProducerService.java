package com.techartistry.accountservice.events.service;

import com.techartistry.accountservice.security.domain.events.UserSignedUpEventData;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, UserSignedUpEventData> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String,UserSignedUpEventData> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishEvent(UserSignedUpEventData eventData) {
        kafkaTemplate.send("account-events", eventData);
    }
}
