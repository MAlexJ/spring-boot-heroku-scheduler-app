package com.malexj.mapper;

import com.malexj.model.dto.ModelEventDto;
import com.malexj.model.entity.ModelEventEntity;
import com.malexj.model.event.ModelEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper:
 */
@Mapper(componentModel = "spring")
public interface ModelEventEntityMapper {
    ModelEventDto evenEntityToDto(ModelEventEntity entity);

    @Mapping(target = "created", ignore = true)
    ModelEventEntity modelEventToModelEventEntity(ModelEvent event);
}
