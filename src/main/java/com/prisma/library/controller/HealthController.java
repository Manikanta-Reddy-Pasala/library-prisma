package com.prisma.library.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// this is for kubernetes health check operations
@RestController
public class HealthController {

    @GetMapping("/health")
    @ApiOperation(value = "API to check health status of the Application")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ready")
    @ApiOperation(value = "API to check Readiness of the Application")
    public ResponseEntity<String> ready() {
        return ResponseEntity.ok().build();
    }
}
