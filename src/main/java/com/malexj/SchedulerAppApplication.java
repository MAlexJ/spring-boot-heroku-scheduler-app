package com.malexj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SchedulerAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulerAppApplication.class, args);
    }

}
