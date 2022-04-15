package com.malexj.tasks;

import com.google.common.io.BaseEncoding;
import com.malexj.event.Event;
import com.malexj.event.ModelEvent;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Log
@Component
@AllArgsConstructor
public class SseEmitterScheduler {

    private final ApplicationEventPublisher publisher;

    @Scheduled(cron = "${scheduled.task.job.cron}")
    public void executionScheduledTask() {
        publisher.publishEvent(buildEvent());
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
