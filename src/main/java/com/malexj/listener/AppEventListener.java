package com.malexj.listener;

import com.malexj.event.ModelEvent;
import lombok.extern.java.Log;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Log
@Component
public class AppEventListener {

    @Async
    @EventListener
    public void handle(ModelEvent event) {
        log.info("AppEventListener : " + event.toString());
    }

}
