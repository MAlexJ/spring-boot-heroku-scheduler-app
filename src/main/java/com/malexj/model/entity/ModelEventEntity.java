package com.malexj.model.entity;


import com.malexj.model.event.Event;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(value = "eventEntity")
public class ModelEventEntity {
    @Id
    private String id;
    private Event event;
    private String message;
    private String scheduler;
    private LocalDateTime created;
}