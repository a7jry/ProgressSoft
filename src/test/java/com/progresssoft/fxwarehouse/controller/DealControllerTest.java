package com.progresssoft.fxwarehouse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.progresssoft.fxwarehouse.dto.DealRequest;
import com.progresssoft.fxwarehouse.dto.DealResponse;
import com.progresssoft.fxwarehouse.service.DealService;
import com.progresssoft.fxwarehouse.exception.DuplicateDealException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DealController.class)
class DealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DealService dealService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createDeal_ValidRequest_Returns200() throws Exception {
        DealRequest request = new DealRequest("D1", "USD", "JOD", Instant.now(), new BigDecimal("100.00"));
        DealResponse response = new DealResponse("D1", "USD", "JOD", request.getDealTimestamp(), request.getDealAmount());

        when(dealService.saveDeal(any(DealRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dealUniqueId").value("D1"));
    }

    @Test
    void createDeal_InvalidRequest_Returns400() throws Exception {
        // Amount is negative, Currency is too long - should trigger validation
        DealRequest request = new DealRequest("D1", "USDD", "JOD", Instant.now(), new BigDecimal("-100.00"));

        mockMvc.perform(post("/api/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.dealAmount").exists())
                .andExpect(jsonPath("$.fromCurrency").exists());
    }

    @Test
    void createDeal_DuplicateDeal_Returns409() throws Exception {
        DealRequest request = new DealRequest("D1", "USD", "JOD", Instant.now(), new BigDecimal("100.00"));

        when(dealService.saveDeal(any(DealRequest.class))).thenThrow(new DuplicateDealException("Duplicate"));

        mockMvc.perform(post("/api/deals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }
}