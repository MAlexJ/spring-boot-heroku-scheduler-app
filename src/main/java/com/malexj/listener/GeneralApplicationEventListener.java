package com.malexj.listener;

import com.malexj.entity.ModelEventEntity;
import com.malexj.event.ModelEvent;
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

    private final ModelEventRepository modelEventRepository;

    @Async
    @EventListener
    public void handleAndSaveModelEventToDb(ModelEvent event) {
        log.info("AppEventListener : " + event.toString());
        ModelEventEntity eventEntity = new ModelEventEntity();
        eventEntity.setId(event.getId());
        eventEntity.setEvent(event.getEvent());
        eventEntity.setMessage(event.getMessage());
        eventEntity.setScheduler(event.getScheduler());
        eventEntity.setCreated(LocalDateTime.now());
        modelEventRepository.save(eventEntity);
    }

}
