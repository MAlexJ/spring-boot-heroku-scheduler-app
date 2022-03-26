package com.malexj.controller;

import com.malexj.model.ErrorDto;
import com.malexj.model.ResponseDto;
import com.malexj.model.Status;
import com.malexj.tasks.FixedRateSchedulerTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

@Log
@RestController
@RequestMapping("/rest/v1/scheduler")
@RequiredArgsConstructor
public class ApiController {

    private static final String SCHEDULER_TASKS = "schedulerTasks";

    private final ScheduledAnnotationBeanPostProcessor scheduledPostProcessor;
    private final FixedRateSchedulerTask fixedRateSchedulerTask;

    @GetMapping("/run")
    public ResponseEntity<ResponseDto> runAllTasks() {
        scheduledPostProcessor.postProcessAfterInitialization(fixedRateSchedulerTask, SCHEDULER_TASKS);
        return ResponseEntity.ok(ResponseDto.builder() //
                .tasks(getTasks()) //
                .status(Status.ACTIVE) //
                .build());
    }

    @GetMapping("/interrupt")
    public ResponseEntity<ResponseDto> interruptTasks() {
        scheduledPostProcessor.postProcessBeforeDestruction(fixedRateSchedulerTask, SCHEDULER_TASKS);
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

    private Set<String> getTasks() {
        return scheduledPostProcessor.getScheduledTasks().stream() //
                .map(ScheduledTask::toString) //
                .collect(Collectors.toSet());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> errorHandler(Exception ex) {
        log.warning(ex.getMessage());
        return ResponseEntity //
                .status(HttpStatus.BAD_REQUEST) //
                .body(ErrorDto.builder().errorMessage(ex.getMessage()).build());
    }

}