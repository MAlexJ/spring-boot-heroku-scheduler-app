package com.malexj.dto;

import com.malexj.event.Event;
import lombok.Data;

@Data
public class EventRequestDto {
    private String id;
    private Event event;
}
