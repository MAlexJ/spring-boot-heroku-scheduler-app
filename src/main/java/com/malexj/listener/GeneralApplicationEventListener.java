package com.malexj.listener;

import com.malexj.entity.ModelEventEntity;
import com.malexj.event.ModelEvent;
import com.malexj.mapper.ModelEventEntityMapper;
import com.malexj.repository.ModelEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Log
@Component
@RequiredArgsConstructor
public class GeneralApplicationEventListener {

    private final ModelEventRepository repository;
    private final ModelEventEntityMapper mapper;

    @Async
    @EventListener
    public void handleAndSaveModelEventToDb(ModelEvent event) {
        ModelEventEntity eventEntity = mapper.eventToModelEntity(event);
        eventEntity.setCreated(LocalDateTime.now());
        repository.save(eventEntity);
    }

}
