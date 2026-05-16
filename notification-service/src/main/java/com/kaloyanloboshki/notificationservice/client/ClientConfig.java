package com.kaloyanloboshki.notificationservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {

    @Bean
    public BookingClient bookingClient(@Value("${booking-service.url}") String bookingServiceUrl) {
        WebClient webClient = WebClient.builder()
                .baseUrl(bookingServiceUrl)
                .build();

        WebClientAdapter adapter = WebClientAdapter.create(webClient);

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder()
                .exchangeAdapter(adapter)
                .build();

        return factory.createClient(BookingClient.class);
    }

    @Bean
    public PreferenceClient preferenceClient(@Value("${preference-service.url}") String preferenceServiceUrl) {
        WebClient webClient = WebClient.builder()
                .baseUrl(preferenceServiceUrl)
                .build();

        WebClientAdapter adapter = WebClientAdapter.create(webClient);

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builder()
                .exchangeAdapter(adapter)
                .build();

        return factory.createClient(PreferenceClient.class);
    }
}
