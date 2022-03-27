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
@Component( value = "CronSSTask")
@AllArgsConstructor
public class CronSchedulerTask {

    private final ApplicationEventPublisher publisher;

    @Scheduled( cron = "*/2 * * * * *")
    public void task() {
        log.info("Run - '" + getClass().getSimpleName() + "', date - " + new Date());
        publisher.publishEvent(buildEvent());
    }

    private ModelEvent buildEvent() {
        return ModelEvent.builder() //
                .id(UUID.randomUUID().toString()) //
                .taskName(getClass().getSimpleName()) //
                .date(new Date()) //
                .message("Message CronSchedulerTask ms") //
                .build();
    }
}
