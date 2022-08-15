package com.malexj.controller.event;

import com.google.common.collect.EvictingQueue;
import com.malexj.exception.SseEmitterException;
import com.malexj.model.SseEmitterWrapper;
import com.malexj.model.event.Event;
import com.malexj.model.event.ModelEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.malexj.model.event.Event.HANDSHAKE_EVENT;

@Log
@RestController
@RequestMapping("/rest/v1")
@RequiredArgsConstructor
public class ServerSentEventsController {

    // Emitter processor
    private ConcurrentMap<String, SseEmitterWrapper> emitterMap;

    // Temp buffer for model event
    private Queue<ModelEvent> evictingSseEventQueue;

    // Max number of stored evens in buffer
    @Value("${sse.evicting-queue.max-size}")
    private int queueMaxSize;

    @PostConstruct
    public void init() {
        emitterMap = new ConcurrentHashMap<>();
        evictingSseEventQueue = EvictingQueue.create(queueMaxSize);
    }

    @Async
    @EventListener
    public void handleAndOfferEventToBuffer(ModelEvent event) {
        Event eventType = event.getEvent();

        // add event to buffer
        if (Event.SSE_EVENT == eventType) {
            evictingSseEventQueue.offer(event);
        }

        // add event to processor
        emitterMap.entrySet().stream() //
                .filter(entry -> eventType == entry.getValue().getEvent()).forEach(entry -> {
                    String id = entry.getKey();
                    SseEmitterWrapper wrapper = entry.getValue();
                    SseEmitter emitter = wrapper.getEmitter();
                    try {
                        emitter.send(buildEmitterEvent(event));
                    } catch (IOException e) {
                        log.info("<<<<< Connection closed: " + id + ", message: " + e.getMessage());
                        if (e.getMessage() != null && e.getMessage().contains("An established connection")) {
                            return;
                        }
                        throw new SseEmitterException(e.getMessage());
                    }
                });
    }

    @GetMapping("/events/subscribe/{eventId}/{event}")
    public SseEmitter subscribeToEmitterByIdAndEvent(@PathVariable String eventId, @PathVariable String event) {
        SseEmitterWrapper emitterWrapper = buildWrapper(eventId, event);
        return Optional.ofNullable(emitterMap.putIfAbsent(eventId, emitterWrapper)) //
                .map(SseEmitterWrapper::getEmitter) //
                .orElse(handShakeEmitter());
    }

    private SseEmitterWrapper buildWrapper(String eventId, String event) {
        SseEmitter emitter = createNewEmitter(eventId);
        return SseEmitterWrapper.builder() //
                .event(Event.parseEvent(event))  //
                .emitter(emitter) //
                .build();
    }

    private SseEmitter createNewEmitter(String id) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitter.onTimeout(() -> emitterMap.remove(id));
        sseEmitter.onCompletion(() -> emitterMap.remove(id));
        sseEmitter.onError(error -> {
            log.warning(error.getMessage());
            throw new SseEmitterException("SseEmitter error : " + error);
        });
        return sseEmitter;
    }

    private SseEmitter.SseEventBuilder buildEmitterEvent(ModelEvent event) {
        String id = event.getId();
        String message = event.getMessage();
        String scheduler = event.getScheduler();
        String eventType = event.getEvent().getEmitterEventName();
        return SseEmitter.event() //
                .id(id) //
                // ote: we can use JSON as data type: MediaType.APPLICATION_JSON
                // using method - data(Object object, @Nullable MediaType mediaType)
                .data(message) //
                .name(eventType) //
                .comment("Comment from " + scheduler + " emitter") //
                .reconnectTime(Long.MIN_VALUE);
    }

    private SseEmitter handShakeEmitter() {
        try {
            SseEmitter sseEmitter = new SseEmitter();
            sseEmitter.send(buildHandShakeEvent());
            sseEmitter.complete();
            return sseEmitter;
        } catch (IOException e) {
            log.severe(e.getMessage());
            throw new SseEmitterException(e.getMessage());
        }
    }

    private SseEmitter.SseEventBuilder buildHandShakeEvent() {
        String id = UUID.randomUUID().toString();
        List<ModelEvent> modelEvents = new ArrayList<>(evictingSseEventQueue);
        return SseEmitter.event() //
                .id(id) //
                .data(modelEvents, MediaType.APPLICATION_JSON) //
                .name(HANDSHAKE_EVENT.getEmitterEventName()) //
                .comment("Comment from empty emitter") //
                .reconnectTime(Long.MIN_VALUE);
    }
}