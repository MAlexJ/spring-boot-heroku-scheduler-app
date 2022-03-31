package com.malexj.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelEvent {
    private String id;
    private Event event;
    private String message;
    private String scheduler;
}
