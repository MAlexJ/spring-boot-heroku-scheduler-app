package com.malexj.mapper;

import com.malexj.dto.ModelEventDto;
import com.malexj.entity.ModelEventEntity;
import com.malexj.event.ModelEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModelEventEntityMapper {
    ModelEventDto evenEntityToDto(ModelEventEntity entity);

    ModelEventEntity eventToModelEntity(ModelEvent event);
}
