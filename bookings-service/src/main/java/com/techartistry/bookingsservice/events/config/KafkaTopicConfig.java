package com.techartistry.bookingsservice.events.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuración de los tópicos de kafka
 */
@Configuration
public class KafkaTopicConfig {
    /**
     * Configura el tópico "payments-events"
     * @return Tópico de kafka
     */
    @Bean
    public NewTopic AccountEventsTopic() {
        Map<String, String> configurations = new HashMap<>();
        //configura la política de limpieza de mensajes, en este caso se eliminarán los mensajes después del tiempo de retención
        configurations.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE);
        //configura el tiempo de retención de mensajes en ms, 86400000 ms = 1 día
        configurations.put(TopicConfig.RETENTION_MS_CONFIG, "86400000");
        //configura el tamaño máximo de segmento en bytes, 1000000000 bytes = 1 GB
        configurations.put(TopicConfig.SEGMENT_BYTES_CONFIG, "1000000000");
        //configura el tamaño máximo de mensaje en bytes, 1000012 bytes = 1 MB
        configurations.put(TopicConfig.MAX_MESSAGE_BYTES_CONFIG, "1000000");

        return TopicBuilder.name("payments-events")
                .partitions(2) //# de particiones
                //.replicas(2) //# de réplicas
                .configs(configurations)
                .build();
    }
}
