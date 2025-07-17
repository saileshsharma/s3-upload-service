package com.ch.carinspection.imgupl.controller;

import com.ch.carinspection.imgupl.service.LogSanitizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/log")
public class LogSanitizerController {

    private final LogSanitizerService logSanitizerService;

    @Autowired
    public LogSanitizerController(LogSanitizerService logSanitizerService) {
        this.logSanitizerService = logSanitizerService;
    }

}
