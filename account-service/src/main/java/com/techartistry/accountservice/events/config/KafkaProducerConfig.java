package com.techartistry.accountservice.events.config;

import com.techartistry.accountservice.security.domain.events.UserSignedUpEventData;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuración del productor de kafka (envío de mensajes)
 */
@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    /**
     * Configura el productor de kafka con las propiedades necesarias
     * @return Productor de kafka
     */
    @Bean
    public ProducerFactory<String, UserSignedUpEventData> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        //configura el servidor de kafka
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        //configura el serializador del mensaje (key)
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        //configura el serializador del objeto (value) -> convertirá clases de java a json
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Configura el template de kafka para enviar mensajes
     * @return Template de kafka
     */
    @Bean
    public KafkaTemplate<String, UserSignedUpEventData> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
