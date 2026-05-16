package com.kaloyanloboshki.notificationservice.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange("/preferences")
public interface PreferenceClient {

    @GetExchange
    List<Long> getUserIdsByPreferenceCategory(@RequestParam String category);
}
