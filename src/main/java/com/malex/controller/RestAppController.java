package com.malex.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
public class RestAppController {

    @GetMapping(value = "/app")
    public Callable<String> get() {
        return () -> "Hello!";
    }
}
