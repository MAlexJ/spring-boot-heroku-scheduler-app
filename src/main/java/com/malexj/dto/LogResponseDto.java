package com.malexj.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LogResponseDto {
    private List<ModelEventDto> modelEventList;
    private long total;
}
