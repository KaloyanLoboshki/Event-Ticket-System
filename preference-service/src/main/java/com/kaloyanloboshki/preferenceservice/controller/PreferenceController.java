package com.kaloyanloboshki.preferenceservice.controller;

import com.kaloyanloboshki.preferenceservice.model.dto.PreferenceRequest;
import com.kaloyanloboshki.preferenceservice.model.entity.Preference;
import com.kaloyanloboshki.preferenceservice.service.PreferenceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("preferences")
public class PreferenceController {

    private final PreferenceService preferenceService;

    public PreferenceController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Preference> findUserById(@PathVariable long userId) {
        return ResponseEntity.ok(preferenceService.findByUserId(userId));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Preference> update(@PathVariable long userId, @Valid @RequestBody PreferenceRequest preferenceRequest) {
        return ResponseEntity.ok(preferenceService.update(userId, preferenceRequest));
    }

    @PostMapping
    public ResponseEntity<Preference> save(@Valid @RequestBody PreferenceRequest preferenceRequest) {
        return ResponseEntity.ok(preferenceService.save(preferenceRequest));
    }

    @GetMapping
    public ResponseEntity<List<Long>> getUserIdsByPreferenceCategory(@RequestParam String category) {
        return ResponseEntity.ok(preferenceService.getUserIdsByPreferenceCategory(category));
    }
}
