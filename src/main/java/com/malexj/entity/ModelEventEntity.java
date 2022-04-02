package com.malexj.entity;


import com.malexj.event.Event;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(value = "eventEntity")
public class ModelEventEntity {
    @Id
    private String id;
    private Event event;
    private String message;
    private String scheduler;
}