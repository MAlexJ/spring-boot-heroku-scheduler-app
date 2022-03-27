package com.malexj.controller;

import com.malexj.event.ModelEvent;
import com.malexj.exception.SseEmitterException;
import com.malexj.model.ErrorDto;
import com.malexj.model.ResponseDto;
import com.malexj.model.Status;
import com.malexj.tasks.CronSchedulerTask;
import com.malexj.tasks.FixedDelaySchedulerTask;
import com.malexj.tasks.FixedRateSchedulerTask;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Log
@RestController
@RequestMapping("/rest/v1/scheduler")
@RequiredArgsConstructor
public class ApiRestController {

    private static final String SCHEDULER_TASKS = "schedulerTasks";

    private final ScheduledAnnotationBeanPostProcessor scheduledPostProcessor;

    private final AtomicInteger atomicInteger = new AtomicInteger();

    /**
     * Scheduler tasks
     */
    private final CronSchedulerTask cronSchedulerTask;
    private final FixedRateSchedulerTask fixedRateSchedulerTask;
    private final FixedDelaySchedulerTask fixedDelaySchedulerTask;

    private ConcurrentMap<String, SseEmitter> emitterMap;

    private Set<String> taskNames;

    @PostConstruct
    public void init() {
        emitterMap = new ConcurrentHashMap<>();
        taskNames = getTasks();
    }

    @Async
    @EventListener
    public void handle(ModelEvent event) {
        Optional.ofNullable(event.getTaskName()) //
                .ifPresent(taskName -> emitterMap.entrySet().stream() //
                        .filter(emitter -> {
                            return extractTaskName(emitter.getKey()).equals(taskName);
                        }) //
                        .forEach(emitter -> {
                            try {
                                SseEmitter sseEmitter = emitter.getValue();
                                String msg = atomicInteger.incrementAndGet() + ": " + event.getDate() + " : " + event.getTaskName();
                                sseEmitter.send(msg);
                            } catch (Exception ex) {
                                log.warning("Error : " + ex);
                                throw new SseEmitterException("Handle event error : " + ex);
                            }
                        }));
    }

    @SneakyThrows
    @GetMapping("/emit/{id}/{action}")
    public SseEmitter emit(@PathVariable String id, @PathVariable String action) {
        return taskNames.stream() //
                .filter(task -> task.contains(action)) //
                .map(task -> {
                    String key = generateCompositeKey(id, action);
                    return emitterMap.computeIfAbsent(key, this::createNewEmitter);
                }) //
                .findFirst() //
                .orElse(emptySseEmitter());
    }

    @GetMapping("/run")
    public ResponseEntity<ResponseDto> runTask(@RequestParam(required = false) String task) {
        if (Objects.nonNull(task)) {
            log.info(task);
        }
        scheduledPostProcessor.postProcessAfterInitialization(fixedRateSchedulerTask, SCHEDULER_TASKS);
        scheduledPostProcessor.postProcessAfterInitialization(cronSchedulerTask, SCHEDULER_TASKS);
        scheduledPostProcessor.postProcessAfterInitialization(fixedDelaySchedulerTask, SCHEDULER_TASKS);
        return ResponseEntity.ok(ResponseDto.builder() //
                .tasks(getTasks()) //
                .status(Status.ACTIVE) //
                .build());
    }

    @GetMapping("/interrupt")
    public ResponseEntity<ResponseDto> interruptTasks() {
        scheduledPostProcessor.postProcessBeforeDestruction(cronSchedulerTask, SCHEDULER_TASKS);
        scheduledPostProcessor.postProcessBeforeDestruction(fixedRateSchedulerTask, SCHEDULER_TASKS);
        scheduledPostProcessor.postProcessBeforeDestruction(fixedDelaySchedulerTask, SCHEDULER_TASKS);
        return ResponseEntity.ok(ResponseDto.builder() //
                .tasks(getTasks()) //
                .status(Status.STOPPED) //
                .build());
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseDto> taskInfo() {
        Set<String> tasks = getTasks();
        return ResponseEntity.ok(ResponseDto.builder() //
                .tasks(tasks) //
                .status(tasks.isEmpty() ? Status.STOPPED : Status.ACTIVE) //
                .build());
    }

    @ExceptionHandler(SseEmitterException.class)
    public ResponseEntity<ErrorDto> errorHandler(Exception ex) {
        log.warning(ex.getMessage());
        return ResponseEntity //
                .status(HttpStatus.BAD_REQUEST) //
                .body(ErrorDto.builder().errorMessage(ex.getMessage()).build());
    }

    private String generateCompositeKey(String id, String action) {
        return id.concat(":").concat(action);
    }

    private String extractTaskName(String idKey) {
        return idKey.substring(idKey.indexOf(':') + 1);
    }

    private SseEmitter emptySseEmitter() throws IOException {
        SseEmitter empty = new SseEmitter();
        empty.send("Action name not found");
        empty.complete();
        return empty;
    }

    private SseEmitter createNewEmitter(String idKey) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitter.onTimeout(() -> emitterMap.remove(idKey));
        sseEmitter.onCompletion(() -> emitterMap.remove(idKey));
        sseEmitter.onError(error -> {
            log.warning(error.getMessage());
            throw new SseEmitterException("SseEmitter error : " + error);
        });
        return sseEmitter;
    }

    private Set<String> getTasks() {
        return scheduledPostProcessor.getScheduledTasks().stream() //
                .map(ScheduledTask::toString) //
                .map(taskName -> taskName.replace(".task", "")) //
                .map(taskName -> taskName.substring(taskName.lastIndexOf('.') + 1)) //
                .collect(Collectors.toSet());
    }
}