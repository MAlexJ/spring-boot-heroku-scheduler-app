package com.malexj.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ResponseDto {
    private Set<String> tasks;
    private Status status;
}
