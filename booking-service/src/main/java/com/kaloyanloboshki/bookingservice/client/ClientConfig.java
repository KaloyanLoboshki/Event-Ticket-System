package com.kaloyanloboshki.bookingservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {

    @Value("${event-service.url}")
    private String eventServiceUrl;

    @Bean
    public EventClient eventClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl(eventServiceUrl)
                .build();
        return HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build()
                .createClient(EventClient.class);
    }
}
