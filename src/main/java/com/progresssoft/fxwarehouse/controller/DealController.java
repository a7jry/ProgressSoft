package com.progresssoft.fxwarehouse.controller;

import com.progresssoft.fxwarehouse.dto.DealRequest;
import com.progresssoft.fxwarehouse.dto.DealResponse;
import com.progresssoft.fxwarehouse.entity.Deal;
import com.progresssoft.fxwarehouse.service.DealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/deals")
@RequiredArgsConstructor
public class DealController {

    private final DealService dealService;

    @PostMapping
    public ResponseEntity<DealResponse> createDeal(@Valid @RequestBody DealRequest request) {
        // Call service to save the deal and get DealResponse
        DealResponse response = dealService.saveDeal(request);
        return ResponseEntity.ok(response);
    }
}
