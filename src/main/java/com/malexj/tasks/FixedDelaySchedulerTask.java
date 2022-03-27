package com.malexj.tasks;

import com.malexj.event.ModelEvent;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Log
@Component
@AllArgsConstructor
public class FixedDelaySchedulerTask {

    private final ApplicationEventPublisher publisher;

    @Scheduled(fixedDelay = 7000L)
    public void task() {
        log.info("Run - '" + getClass().getSimpleName() + "', date - " + new Date());
        publisher.publishEvent(buildEvent());
    }

    private ModelEvent buildEvent() {
        return ModelEvent.builder() //
                .id(UUID.randomUUID().toString()) //
                .taskName(getClass().getSimpleName()) //
                .date(new Date()) //
                .message("New message : INFO_MSG") //
                .build();
    }

}
