package com.malexj.service;

import com.malexj.mapper.ModelEventEntityMapper;
import com.malexj.model.dto.ModelEventDto;
import com.malexj.model.entity.ModelEventEntity;
import com.malexj.repository.ModelEventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public record EventLogsService(ModelEventRepository repository, ModelEventEntityMapper mapper) {

    public Page<ModelEventDto> findAllLogsByPageable(Pageable pageable) {
        Page<ModelEventEntity> entities = repository.findAll(pageable);
        return entities.map(mapper::evenEntityToDto);
    }

}