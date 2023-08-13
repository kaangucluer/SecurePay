package com.firisbe.securepay.controller;

import com.firisbe.securepay.model.Log;
import com.firisbe.securepay.service.LogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/log")
public class LogController {
    private final LogService logService;

    @GetMapping("/all")
    public ResponseEntity<List<Log>> getAllLog(){
    return ResponseEntity.status(HttpStatus.OK)
            .body(logService.getAllLog());
    }

    public LogController(LogService logService) {
        this.logService = logService;
    }
}
