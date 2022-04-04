package com.malexj.mapper;

import com.malexj.dto.ModelEventDto;
import com.malexj.entity.ModelEventEntity;
import com.malexj.event.ModelEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct documentation:
 * https://mapstruct.org/documentation/stable/reference/html/
 */
@Mapper(componentModel = "spring")
public interface ModelEventEntityMapper {
    ModelEventDto evenEntityToDto(ModelEventEntity entity);

    @Mapping(target = "created", ignore = true)
    ModelEventEntity modelEventToModelEventEntity(ModelEvent event);
}
