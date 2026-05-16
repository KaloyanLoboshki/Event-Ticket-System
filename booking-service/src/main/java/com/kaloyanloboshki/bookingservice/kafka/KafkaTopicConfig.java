package com.kaloyanloboshki.bookingservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic ticketBookedTopic() {
        return TopicBuilder.name("ticket-booked")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic ticketCancelledTopic() {
        return TopicBuilder.name("ticket-cancelled")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
