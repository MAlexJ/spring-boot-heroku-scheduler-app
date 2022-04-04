package com.malexj.service;

import com.malexj.dto.ModelEventDto;
import com.malexj.entity.ModelEventEntity;
import com.malexj.mapper.ModelEventEntityMapper;
import com.malexj.repository.ModelEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventLogsService {

    private final ModelEventRepository repository;
    private final ModelEventEntityMapper mapper;

    public Page<ModelEventDto> findAllLogsByPageable(Pageable pageable) {
        Page<ModelEventEntity> entities = repository.findAll(pageable);
        return entities.map(mapper::evenEntityToDto);
    }

}