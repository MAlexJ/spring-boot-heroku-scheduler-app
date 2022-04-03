package com.malexj.controller;

import com.malexj.dto.LogResponseDto;
import com.malexj.service.EventLogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/v1/logs")
public class LogsRestController {

    private final EventLogsService service;

    @GetMapping
    public ResponseEntity<LogResponseDto> findLogs() {
        return ResponseEntity.ok(service.findAllLogs());
    }

}
