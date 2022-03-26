package com.malexj.tasks;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@Log
public class FixedRateSchedulerTask {

    private static final String INFO_MSG = "Execute the annotated method with a fixed period between invocations.";

    // Execute the annotated method with a fixed period between invocations.
    @Scheduled(fixedRate = 3000L)
    public void task() {
        log.info("Run - '" + getClass().getSimpleName() + "', date - " + new Date() + ", info - " + INFO_MSG);
    }

}
