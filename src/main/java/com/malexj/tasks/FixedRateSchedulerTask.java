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
public class FixedRateSchedulerTask {

    private final ApplicationEventPublisher publisher;

    @Scheduled(fixedRate = 5000L)
    public void task() {
        log.info("Execute " + getClass().getSimpleName() + "' scheduler, date - " + new Date());
        publisher.publishEvent(buildEvent());
    }

    private ModelEvent buildEvent() {
        return ModelEvent.builder() //
                .id(UUID.randomUUID().toString()) //
                .taskName(getClass().getSimpleName()) //
                .date(new Date()) //
                .message("INFO_MSG + FixedRateSchedulerTask") //
                .build();
    }

}
