package com.malexj.scheduler;

import com.google.common.io.BaseEncoding;
import com.malexj.model.event.Event;
import com.malexj.model.event.ModelEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Log
@Component
@RequiredArgsConstructor
public class SseEmitterScheduler {

    private final ApplicationEventPublisher publisher;

    @Scheduled(cron = "${scheduled.task.job.cron}")
    public void executionScheduledTask() {
        // 1. does some work
        ModelEvent modelEvent = buildEvent();
        // 2. publishes the result
        publisher.publishEvent(modelEvent);
    }

    private ModelEvent buildEvent() {
        return ModelEvent.builder() //
                .id(UUID.randomUUID().toString()) //
                .event(Event.SSE_EVENT) //
                .message("Message CronSchedulerTask, value: " + getRandom().nextInt(Integer.MAX_VALUE) + ", " + generate()) //
                .scheduler(getClass().getSimpleName()) //
                .build();
    }

    String generate() {
        final byte[] buffer = new byte[5];
        getRandom().nextBytes(buffer);
        return BaseEncoding.base64Url().omitPadding().encode(buffer); // or base32()
    }

    Random getRandom() {
        return new Random();
    }

}
