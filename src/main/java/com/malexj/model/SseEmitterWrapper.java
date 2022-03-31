package com.malexj.model;

import com.malexj.event.Event;
import lombok.Builder;
import lombok.Value;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Value
@Builder
public class SseEmitterWrapper {
    Event event;
    SseEmitter emitter;
}
