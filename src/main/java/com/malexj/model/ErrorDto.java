package com.malexj.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    public String errorMessage;
}
