package com.malexj.event;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ModelEvent {
    private String id;
    private String taskName;
    private Date date;
    private String message;
}
