package com.malexj.tasks;

import com.malexj.event.Event;
import com.malexj.event.ModelEvent;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Log
@Component
@AllArgsConstructor
public class SseEmitterScheduler {

    private final ApplicationEventPublisher publisher;

    @Scheduled(cron = "*/5 * * * * *")
    public void task() {
        log.info("SseEmitterScheduler : " + getClass().getSimpleName() + "', date - " + new Date());
        publisher.publishEvent(buildEvent());
    }

    private ModelEvent buildEvent() {
        return ModelEvent.builder() //
                .id(UUID.randomUUID().toString()) //
                .event(Event.SSE_EVENT) //
                .message("Message CronSchedulerTask, value: " + new Random().nextInt(Integer.MAX_VALUE)) //
                .scheduler(getClass().getSimpleName()) //
                .build();
    }
}
