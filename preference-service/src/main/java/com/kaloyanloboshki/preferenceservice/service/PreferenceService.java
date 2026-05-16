package com.kaloyanloboshki.preferenceservice.service;

import com.kaloyanloboshki.preferenceservice.exceptions.PreferenceNotFoundException;
import com.kaloyanloboshki.preferenceservice.mapper.PreferenceMapper;
import com.kaloyanloboshki.preferenceservice.model.dto.PreferenceRequest;
import com.kaloyanloboshki.preferenceservice.model.entity.Preference;
import com.kaloyanloboshki.preferenceservice.repository.PreferenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferenceService {

    public static final String PREFERENCE_NOT_FOUND_MESSAGE = "Preference for user id: %d does not exist!";

    private final PreferenceRepository preferenceRepository;
    private final PreferenceMapper preferenceMapper;

    public PreferenceService(PreferenceRepository preferenceRepository, PreferenceMapper preferenceMapper) {
        this.preferenceRepository = preferenceRepository;
        this.preferenceMapper = preferenceMapper;
    }

    public Preference findByUserId(long userId) {
        return preferenceRepository.findByUserId(userId);
    }

    public Preference update(long userId, PreferenceRequest preferenceRequest) {
        Preference preference = preferenceRepository.findOptionalByUserId(userId)
                .orElseThrow(() -> new PreferenceNotFoundException(String.format(PREFERENCE_NOT_FOUND_MESSAGE, userId)));

        preferenceMapper.updateEventFromDto(preferenceRequest, preference);

        return preferenceRepository.save(preference);
    }

    public List<Long> getUserIdsByPreferenceCategory(String category) {
        return preferenceRepository.getPreferenceByPreferredCategory(category);
    }
}
