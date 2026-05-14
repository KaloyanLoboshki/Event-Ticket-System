package com.kaloyanloboshki.eventservice.mapper;

import com.kaloyanloboshki.eventservice.model.dto.EventCreateRequest;
import com.kaloyanloboshki.eventservice.model.dto.EventResponse;
import com.kaloyanloboshki.eventservice.model.dto.EventUpdateRequest;
import com.kaloyanloboshki.eventservice.model.entity.Event;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventResponse toResponse(Event event);

    Event toEntity(EventCreateRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEventFromDto(EventUpdateRequest request, @MappingTarget Event event);
}
