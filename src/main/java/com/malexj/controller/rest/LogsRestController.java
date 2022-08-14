package com.malexj.controller.rest;

import com.malexj.model.dto.ModelEventDto;
import com.malexj.service.EventLogsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/v1")
public class LogsRestController {

    private final EventLogsService service;

    @GetMapping("/logs")
    public ResponseEntity<Page<ModelEventDto>> findLogs(@PageableDefault(sort = {"created"}) Pageable pageable) {
        log.info("REST API: findLogs by : " + pageable);
        return ResponseEntity.ok(service.findAllLogsByPageable(pageable));
    }

    // todo error Handler !!!!!

}
