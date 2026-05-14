package com.kaloyanloboshki.bookingservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {

    @Bean
    public EventClient eventClient(@Value("${event-service.url}") String eventServiceUrl) {
        WebClient webClient = WebClient.builder()
                .baseUrl(eventServiceUrl)
                .build();

        WebClientAdapter adapter = WebClientAdapter.create(webClient);

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder()
                .exchangeAdapter(adapter)
                .build();

        return factory.createClient(EventClient.class);
    }
}
