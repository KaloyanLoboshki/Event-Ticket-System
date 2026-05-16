package com.kaloyanloboshki.preferenceservice.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "preferences")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column(nullable = false)
    private String preferredCategory;

    private String preferredLocation;

    private Double maxPrice;
}
