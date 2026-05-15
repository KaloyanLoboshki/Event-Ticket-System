package com.kaloyanloboshki.eventservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic eventCreationTopic() {
        return TopicBuilder.name("event-creation")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic eventUpdatedTopic() {
        return TopicBuilder.name("event-updated")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
