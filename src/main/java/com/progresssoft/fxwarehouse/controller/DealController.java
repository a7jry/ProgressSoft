package com.progresssoft.fxwarehouse.controller;

import com.progresssoft.fxwarehouse.dto.DealRequest;
import com.progresssoft.fxwarehouse.dto.DealResponse;
import com.progresssoft.fxwarehouse.service.DealService;
import jakarta.validation.Valid; // IMPORTANT: Required for validation
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DealController {

    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @PostMapping("/deals")
    // Adding @Valid here tells Spring to check the annotations inside DealRequest
    public ResponseEntity<DealResponse> createDeal(@Valid @RequestBody DealRequest request) {
        DealResponse response = dealService.saveDeal(request);
        return ResponseEntity.ok(response);
    }
}
