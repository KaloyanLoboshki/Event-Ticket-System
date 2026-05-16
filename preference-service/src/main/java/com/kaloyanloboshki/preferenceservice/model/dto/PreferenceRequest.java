package com.kaloyanloboshki.preferenceservice.model.dto;

import lombok.Data;

@Data
public class PreferenceRequest {

    private Long userId;

    private String preferredCategory;

    private String preferredLocation;

    private Double maxPrice;
}
