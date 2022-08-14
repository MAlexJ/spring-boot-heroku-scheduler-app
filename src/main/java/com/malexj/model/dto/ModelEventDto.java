package com.malexj.model.dto;

import com.malexj.model.event.Event;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ModelEventDto {
    private String id;
    private Event event;
    private String message;
    private String scheduler;
    private LocalDateTime created;
}
