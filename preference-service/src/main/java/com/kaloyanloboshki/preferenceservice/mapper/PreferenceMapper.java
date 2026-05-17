package com.kaloyanloboshki.preferenceservice.mapper;

import com.kaloyanloboshki.preferenceservice.model.dto.PreferenceRequest;
import com.kaloyanloboshki.preferenceservice.model.entity.Preference;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface PreferenceMapper {

    Preference toPreferenceEntity(PreferenceRequest preferenceRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEventFromDto(PreferenceRequest preferenceRequest, @MappingTarget Preference preference);
}
