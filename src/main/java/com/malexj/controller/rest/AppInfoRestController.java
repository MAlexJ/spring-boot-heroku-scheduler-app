package com.malexj.controller.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.boot.info.BuildProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/v1")
public class AppInfoRestController {

    /**
     * Provide build-related information such as group and artifact:
     * "name" -  name of the project
     * "time" -  timestamp of the build
     * "version" - version of the project
     * "group" - groupId
     * "artifact" - artifactId of the project
     */
    private final BuildProperties buildProperties;

    @GetMapping("/info")
    public ResponseEntity<BuildProperties> appInfo() {
        return ResponseEntity.ok(buildProperties);
    }

}
