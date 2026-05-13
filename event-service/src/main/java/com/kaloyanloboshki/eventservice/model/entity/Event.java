package com.kaloyanloboshki.eventservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;

@Entity
@Table(name = "events")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String title;

    @NotBlank
    private String location;

    @NotBlank
    private String category;

    @NotBlank
    private String description;

    @NonNull
    private ZonedDateTime startTime;

    @NonNull
    private ZonedDateTime endTime;

    @NonNull
    private BigDecimal price;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
